# java基础工具类方法

本项目主要收录了在个人开发过程中，使用到的常用的个人开发工具，包括线程池的使用，阿里巴巴的标准规范，jcraft的使用，jcifs工具包的使用等

## jsch使用

jsch是一个基于ssh2的java的开源包，它允许你利用该开源工具连接到对应的一个sshd的服务器中，进行命令的调用，文件流获取，文件复制，上传等功能，常用的工具调用方式
```
  Session sshSession = null;  
  Channel channel = null;  
  ChannelSftp sftp = null;  
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
```
获取对应的Channel，Channel的一个实现类

 - [ ] 获取文件流操作
```
ChanneSft.get(String filepath)
```
 - [ ] 调用命令（将调用命令获取的结果转换成对应的IO流数据）
 ```
session = sft.getSession();  
channelExec = (ChannelExec) session.openChannel("exec");  
channelExec.setCommand(cmd);  
channelExec.setInputStream(null);  
channelExec.setErrStream(System.err);  
channelExec.connect();  
InputStream in = channelExec.getInputStream();
 ```

## SMB jcifs

使用SMB jcifs对window服务器的共享文件夹中的文件数据进行操作。
利用第三方：

　　CIFS (Common Internet File System)

　　SMB(Server Message Block）

　　CIFS是公共的或开放的SMB协议版本，并由Microsoft使用。SMB协议(见最后的名词解释)现在是局域网上用于服务器文件访问和打印的协议。象SMB协议一样，CIFS在高层运行，而不象TCP/IP协议那样运行在底层。CIFS可以看做是应用程序协议如文件传输协议和超文本传输协议的一个实现。
　　CIFS的作用：
```
1.访问服务器本地文件并读写这些文件 

2.与其它用户一起共享一些文件块 

3.在断线时自动恢复与网络的连接 

4.使用西欧字符文件名
```

## ini文件解析

通过工具将ini文件中的sections结果下的parameters文件进行解析，归纳



## 线程池的使用规范
```
ThreadFactory namedThreadFactory = new 	ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
ExecutorService executorService = new ThreadPoolExecutor(5, 10, 0L, TimeUnit.MILLISECONDS,new LinkedBlockingDeque<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
```

## apache Lucence使用

## metrics-core使用
```
    Metrics五种主要的类型：
       1,Gauges:是一种最简单的计量，一般用于统计瞬时状态的数据信息，比如系统处于pending状态的job
       2,Counter:是Gauges的一个特例，维护一个计数器，可以通过inc()和dec()方法对计数器做修改。使用步骤与Gauge基本类似，在MetricRegistry中提供了静态方法可以直接实例化一个Counter。
       3,Meters:Meters用来度量某个时间段的平均处理次数（request per second），每1、5、15分钟的TPS。比如一个service的请求数，通过metrics.meter()实例化一个Meter之后，然后通过meter.mark()方法就能将本次请求记录下来。统计结果有总的请求数，平均每秒的请求数，以及最近的1、5、15分钟的平均TPS。
       4,Histograms:Histograms主要使用来统计数据的分布情况，最大值、最小值、平均值、中位数，百分比（75%、90%、95%、98%、99%和99.9%）。例如，需要统计某个页面的请求响应时间分布情况，可以使用该种类型的Metrics进行统计。
       5,Timers:Timers主要是用来统计某一块代码段的执行时间以及其分布情况，具体是基于Histograms和Meters来实现的
    具体使用规则：
        https://www.cnblogs.com/nexiyi/p/metrics_sample_1.html
```
## 包格式
```
    每一个包代表的是对应的每一个知识点的回顾数据
```
## 新建develop分支
```
整理表结构，新的分支更加合理的代码逻辑
```