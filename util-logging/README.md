## java 常用的日志框架
[常见日志框架详解](https://www.cnblogs.com/chenhongliang/p/5312517.html#java%E6%97%A5%E5%BF%97%E6%A6%82%E8%BF%B0)

### slf4j (日志的门面接口slf4j-api)

Slf4j的设计思想比较简洁，使用了Facade设计模式，Slf4j本身只提供了一个slf4j-api-version.jar包
这个jar中主要是日志的抽象接口，jar中本身并没有对抽象出来的接口做实现。


slf4j 门面接口

针对不同的日志的实现类(桥接类)：
![avatar](photo/20211201143956.jpg)



### commons logging