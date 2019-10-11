package com.common.tools.jcifs;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhoucg
 * @date 2018-11-29
 * 使用CIFS SMB 第三方公共包操作window系统共享文件盘数据
 * <p>
 *     CIFS是公共的或开放的SMB协议版本，并由Microsoft使用。SMB协议现在是局域网上用于服务器文件访问和打印的协议。
 *     象SMB协议一样，CIFS在高层运行，而不象TCP/IP协议那样运行在底层。CIFS可以看做是应用程序协议如文件传输协议和超文本传输协议的一个实现。
 * </p>
 */
public class JcifsCommonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JcifsCommonUtils.class);

    /**
     * 协议名称
     */
    private static final String PROTOCOL_PRE = "smb://";

    /**
     * 解析window系统共享文件盘指定盘符，获取指定文件结果数据
     * @param remoteHost
     * @param remoteUserName
     * @param remotePassword
     * @param remteDir
     * @return
     */
    public static Map<String,Object> deepParseSmbDir(String remoteHost, String remoteUserName,
                                                     String remotePassword, String remteDir) throws Exception{

        logger.info("ip地址：{},用户名：{}，密码：{}，文件路径：{}",remoteHost,remoteUserName,remotePassword,remteDir);

        Map<String,Object> smbMap = new HashMap<>(16);
        smbMap.put("hostIp",remoteHost);
        smbMap.put("diskUseRate","62%");

        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(PROTOCOL_PRE + remoteHost,remoteUserName,remotePassword );
        logger.info("当前获取auth:{}",auth);

        /**
         * 共享文件夹远程url
         */
        String remoteUrl = PROTOCOL_PRE + remoteUserName + ":" + remotePassword + "@" + remoteHost + "/" + remteDir + "/";
        logger.info("remoteUrl:{}",remoteUrl);

        try{
            SmbFile file = new SmbFile(remoteUrl,auth);
            if(file.isDirectory()) {
                List<Map<String,String>> fileList = new ArrayList<>();
                SmbFile[] files = file.listFiles();
                /**
                 * behz
                 * SL0
                 * SL1
                 */
                for (SmbFile smbFile : files) {
                    if(smbFile.isDirectory()) {

                        if(smbFile.getName().contains("behz")) {
                            continue;
                        }
                        /**
                         * 统计当前<p>remteDir</p>下为文件夹的资料进行统计
                         */
                        Map<String,String> smbDirMap = new HashMap<>(16);
                        String chkDsk = smbFile.getName();
                        if(chkDsk.indexOf("/") != -1) {
                            int lastIndexOf = chkDsk.lastIndexOf("/");
                            chkDsk = chkDsk.substring(0,lastIndexOf);
                        }
                        smbDirMap.put("chkdsk",chkDsk);
                        int total = recursionFile(smbFile);
                        Long length = smbFile.length();
                        logger.info("current scan sourceName:{},scan total:{},scan length:{}",smbFile.getName(),total,length);
                        smbDirMap.put("total",String.valueOf(total));
                        fileList.add(smbDirMap);
                    }
                }
                smbMap.put("diskList",fileList);

            }

        } catch (MalformedURLException e) {
            logger.error("current scan the filepath:{} error ,current error message:{}",remoteUrl,e.getMessage());
            throw e;
        }

        return smbMap;

    }

    /**
     * 递归的方式获取指定文件目录下的目录信息
     * @param smbFile
     * @return
     */
    private static int recursionFile(SmbFile smbFile) throws MalformedURLException,SmbException {
        int i=0;
        SmbFile[] dirFiles = smbFile.listFiles();
        for (SmbFile dirFile : dirFiles) {
            if(!dirFile.isDirectory()) {
                i++;
            } else {
                i+= recursionFile(dirFile);
            }
        }
        return i;
    }
}
