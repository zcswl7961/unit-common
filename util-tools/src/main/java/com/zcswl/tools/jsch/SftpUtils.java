package com.zcswl.tools.jsch;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 远程linux系统，将linux系统目录下的指定文件夹，
 * 调用命令
 * @author zhoucg
 * @date 2018-11-29
 */
public class SftpUtils {

    private static final Logger logger = LoggerFactory.getLogger(SftpUtils.class);

    /**
     * 新增线程池操作获取sftp连接
     * 使用高性能的ConcurrentHashMap作为线程池
     */
    private static final ConcurrentHashMap<String,Channel> SFTP_CHANNEL_POOL = new ConcurrentHashMap<>();

    /**
     * 默认编码格式
     */
    private static final String CHARSET_UTF8 = "UTF-8";


    /**
     * split the colonyPropery
     */
    private static final String SPLIT_COMMA =",";

    /**
     * split the colony value
     */
    private static final String SPLIT_POUND_SINGLE = ":";



    /**
     * CMACAST集群数据获取,设置CMACAST集群连接的随机性
     * @param colonyPropery 指定sft格式  host:port:username:password,host:name:username:password
     * @param serial sftp 连接编号
     * @return 连接句柄
     */
    public static ChannelSftp getSftpConnectionColony(final String colonyPropery,String serial) {

        int currentRetry = 0;

        Channel channel ;
        ChannelSftp sftp ;
        String[] colonyArrs = colonyPropery.split(SPLIT_COMMA);
        List<String> providers = Arrays.asList(colonyArrs);
        Collections.shuffle(providers);
        LABEL:for(;;) {
            for(String colony : providers) {
                String[] property = colony.split(SPLIT_POUND_SINGLE);
                String host = property[0];
                int port = Integer.parseInt(property[1]);
                String userName = property[2];
                String password = property[3];
                try{
                    currentRetry++;
                    if(currentRetry > colonyArrs.length * 4) {
                        sftp = null;
                        break LABEL;
                    }
                    String key = host + "," + port + "," + userName + "," +password + "," + serial;
                    channel = doConnection(host,port,userName,password,key);
                    logger.info("[SYSTEM] channel success current host:{},port:{},userName:{},password:{}",host,port,userName,password);
                    sftp = (ChannelSftp) channel;
                    if(sftp != null) {
                        break LABEL;
                    }
                } catch ( Exception e) {
                    logger.error("channel faile go continue current host:{},port:{},userName:{},password:{}",host,port,userName,password);
                }

            }
        }

        return sftp;
    }

    /**
     * CMACAST释放连接
     * @param colonyPropery 指定sft格式  host:port:username:password,host:name:username:password
     * @param serial  sftp 连接编号
     */
    public static void relase(final String colonyPropery,String serial) {
        String[] colonyArrs = colonyPropery.split(SPLIT_COMMA);
        for(String colony : colonyArrs) {
            String[] property = colony.split(SPLIT_POUND_SINGLE);
            String host = property[0];
            int port = Integer.parseInt(property[1]);
            String userName = property[2];
            String password = property[3];
            String key = host + "," + port + "," + userName + "," +password + "," + serial;
            if(SFTP_CHANNEL_POOL.contains(key)) {
                SFTP_CHANNEL_POOL.remove(key);
            }
        }
    }


    /**
     * 多线程下获取ftp连接
     * @param host 主机地址
     * @param port 主机端口
     * @param userName 用户名
     * @param password 密码
     * @param threadId 线程id
     * @return 连接句柄
     */
    public static ChannelSftp getSftpConnection(final String host, final int port, final String userName,
                                                final String password, final long threadId) throws JSchException {
        Channel channel = null;
        ChannelSftp sftp = null;
        String key = host + "," + port + "," + userName + "," +password + "," +threadId;
        channel = doConnection(host,port,userName,password,key);
        sftp = (ChannelSftp) channel;
        return sftp;
    }

    /**
     * 单线程下获取连接
     * @param host 主机ip
     * @param port 主机端口
     * @param userName 用户名
     * @param password 密码
     * @return 连接句柄
     */
    public static ChannelSftp getSftpConnection(final String host,final int port,final String userName,
                                                final String password) throws JSchException {
        Channel channel = null;
        ChannelSftp sftp = null;
        String key = host + "," + port + "," + userName + "," +password;
        channel = doConnection(host,port,userName,password,key);
        sftp = (ChannelSftp) channel;
        return sftp;
    }


    /**
     * 使用Map存储对应的sftp连接，连接池
     * @param host 主机
     * @param port 端口
     * @param userName 用户名
     * @param password 密码
     * @param key 指定key
     * @return
     * @throws JSchException 异常抛出
     */
    public static Channel doConnection (String host,int port,String userName,String password,String key) throws JSchException {
        Session sshSession;
        Channel channel;
        if(null == SFTP_CHANNEL_POOL.get(key)) {
            JSch jsch = new JSch();
            jsch.getSession(userName, host, port);
            sshSession = jsch.getSession(userName, host, port);
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            channel = sshSession.openChannel("sftp");
            channel.connect();
            SFTP_CHANNEL_POOL.put(key, channel);
        } else {
            channel = SFTP_CHANNEL_POOL.get(key);
            sshSession = channel.getSession();
            if (!sshSession.isConnected()) {
                sshSession.connect();
            }
            if (!channel.isConnected()) {
                channel.connect();
            }
        }
        return channel;
    }


    /**
     * 关闭协议-sftp协议.(关闭会导致连接池异常，因此不建议用户自定义关闭)
     * @param sftp sftp连接
     */
    public static void exit(final ChannelSftp sftp ) {
        if(sftp != null) {
            sftp.exit();
        }
    }


    /**
     * 调用ssh命令，返回输出命令的结果信息
     * @param cmd
     * @param charset
     * @param sft
     * @return BufferedReader 返回输出结果的字符串
     * @throws Exception
     */
    public static BufferedReader runCmd(String cmd, String charset, final ChannelSftp sft) throws Exception{
        Session session;
        ChannelExec channelExec;
        try{
            session = sft.getSession();
            channelExec = (ChannelExec) session.openChannel("exec");

            channelExec.setCommand(cmd);
            channelExec.setInputStream(null);
            channelExec.setErrStream(System.err);
            channelExec.connect();
            InputStream in = channelExec.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            return reader;
        } catch (Exception e) {
            e.getMessage();
            throw e;
        }
    }

    /**
     * 将远程的文件转换成io流的方式返回
     * @param filePath
     * @param sftp
     * @return
     */
    public static InputStream readFile(final String filePath,final ChannelSftp sftp) {
        InputStream in = null;
        try{
            in = sftp.get(filePath);
        } catch (Exception e ){
            logger.error("当前读取文件:{}错误，错误堆栈信息:{}",filePath,e.getMessage());
        }
        return in;
    }

    /**
     * 将远程的文件夹下的目录，获取最新的创建时间的 newLength 个数据
     * ls -lt /dirname/ | grep filename | head -n 1 |awk '{print $9}'
     * /dirname/：指定的文件夹目录
     * grep filename :匹配的文件名信息
     * 1:表示显示最新的几个数据信息
     * @param fileDir
     * @param sftp
     * @param least
     * @return
     *          list:
     *              key:resourceName -> value:notice message
     */
    public static List<Map<String,String>> readDir(final String fileDir, final ChannelSftp sftp, int least) {
        List<Map<String,String>> newMapList = new ArrayList<>(16);
        String searchCmd = "ls -lt " + fileDir + " | grep notice | head -n " + least;
        logger.info("curret query readDir cmd = "+searchCmd);
        BufferedReader bufferedReader;
        try{
            bufferedReader = runCmd(searchCmd,CHARSET_UTF8,sftp);
            String line;
            while((line = bufferedReader.readLine()) != null) {
                logger.info("current line：{}",line);
                Map<String,String> newMap = new HashMap<>(4);
                String[] lineArr = line.split(" ");
                String linePath = lineArr[lineArr.length-1];
                String fullPath = fileDir + linePath;
                StringBuilder stringBuilder = new StringBuilder();
                InputStream in = readFile(fullPath,sftp);
                BufferedReader localReader = new BufferedReader(new InputStreamReader(in));
                int i = 0;
                String localLine;
                while((localLine = localReader.readLine()) != null) {
                    i++;
                    if(i == 6) {
                        /**
                         * 主题信息
                         */
                        newMap.put("noticeTitle",localLine);
                    }
                    if( i > 6) {
                        stringBuilder.append(localLine);
                    }
                }
                newMap.put(fullPath,stringBuilder.toString());
                newMapList.add(newMap);
                in.close();
                localReader.close();
            }
        } catch ( Exception e) {
            logger.error("SftpUtil query readDir error , fileDir:{},cmd:{},error message{}",fileDir,searchCmd,e);
            return newMapList;
        }
        close(bufferedReader);
        return newMapList;
    }

    /**
     * 关闭流
     * @param reader
     */
    private static void close(Reader reader) {
        try{
            if( reader != null) {
                reader.close();
            }
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }





}
