package com.zcswl.mybatis.serivce;

import com.zcswl.mybatis.entity.MyUser;
import com.zcswl.mybatis.mapper.MyUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyUserService {

    @Autowired
    private MyUserMapper myUserMapper;

    public void insert(){
        MyUser myUser = new MyUser();
        //myUser.setId(100L);
        myUser.setName("张着口");
        //myUserMapper.insertSelective(myUser);
        myUserMapper.insertInner(myUser);
        System.out.println(myUser);
        myUserMapper.insert(myUser);
        System.out.println(myUser);
    }

    public MyUser getById(Integer userId){
         MyUser myUser = myUserMapper.selectById(userId);
        return myUser;
    }

}
