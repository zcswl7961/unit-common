package com.common.util.spring.action;

/**
 * Created by zhoucg on 2019-03-19.
 */
public interface IActorDao {

    void save(String firstName,String lastName);

    String query(String firstName);

}
