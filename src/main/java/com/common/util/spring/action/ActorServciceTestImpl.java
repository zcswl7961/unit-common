package com.common.util.spring.action;

import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhoucg on 2019-03-19.
 */
@Transactional
public class ActorServciceTestImpl implements ActorServciceTest{

    private IActorDao iActorService;


    public IActorDao getiActorService() {
        return iActorService;
    }

    public void setiActorService(IActorDao iActorService) {
        this.iActorService = iActorService;
    }

    @Override
    public void test() {
        iActorService.query("zcg");
        iActorService.save("zcg","wl");
    }

}


