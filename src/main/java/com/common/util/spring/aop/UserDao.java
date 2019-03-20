package com.common.util.spring.aop;

/**
 * Created by zhoucg on 2019-03-15.
 * AOP 定义接口
 */
public interface UserDao {

    int addUser();

    void updateUser();

    void deleteUser();

    void findUser();


}
