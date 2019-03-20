package com.common.util.spring.aop;

import com.common.util.spring.aop.UserDao;

/**
 * Created by zhoucg on 2019-03-15.
 */
public class UserDaoImpl implements UserDao {
    @Override
    public int addUser() {
        System.out.println("add user .............");
        return 0;
    }

    @Override
    public void updateUser() {
        System.out.println("update user ......");
    }

    @Override
    public void deleteUser() {
        System.out.println("delete user ......");
    }

    @Override
    public void findUser() {
        System.out.println("find user ......");
    }
}
