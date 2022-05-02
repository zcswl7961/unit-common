事务：http://www.tianshouzhi.com/api/tutorials/distributed_transaction/383

## 分布式事务

分布式事物模型：DTP

两阶段提交：XA定一个了TM和RM之间的通信接口。并通过两阶段提交的策略进行事务，实际上，XA对两阶段提交进行了优化（）

TCC:try commit cancel
两阶段：Prepare：TM发送prepare请求到各个RM，RM收到prepare请求之后执行数据修改和日志记录等信息，处理完成之后只会
               事务的完成状态改成可以提交，并返回结果个TM
      Commit ：TM根据阶段1各个RM prepare的结果，决定是提交还是回滚事务。如果所有的RM都prepare成功，那么TM通知所有的RM进行提交；
               如果有RM prepare失败的话，则TM通知所有RM回滚自己的事务分支。      
               
XA对两阶段提交的优化：
    1，只读断言
    2，如果需要增删改的数据都在同一个RM上，TM可以使用一阶段提交——跳过两阶段提交中的Phase 1，直接执行Phase 2。
    
两阶段提交的问题：

由此引入的三阶段提交

三阶段提交引入了超时机制


分布式系统理论CAP：
CAP：一致性，可用性（可用性是指系统提供的服务必须一直处于可用的状态，对于用户的每一次的操作总是在有限的时间内返回结果），分区容忍性
    CA：舍去P分区容忍性
    CP：可用性差，强调分布式的一致性，Zookeeper
    AP：一致性差，弱一致性（最终一致性）Eureka


BASE理论与柔性事物：







### atomikos