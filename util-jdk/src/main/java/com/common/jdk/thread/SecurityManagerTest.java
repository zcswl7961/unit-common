package com.common.jdk.thread;

/**
 * 可以通过-Djava.security.manager 参数来获取当前系统默认的安全管理器
 *
 * 默认的安全管理器的配置文件：JDK\jre\lib\security\java.policy
 *
 *
 * 我们同样可以使用自定义的配置文件：VM options:-Djava.security.policy="...\JavaExample\src\test\resources\conf\java.policy"
 * @author zhoucg
 * @date 2021-01-20 14:30
 */
public class SecurityManagerTest {

    public static void main(String[] args) {
        SecurityManager securityManager = System.getSecurityManager();
        System.out.println("默认的获取securityManager="+securityManager);
    }
}
