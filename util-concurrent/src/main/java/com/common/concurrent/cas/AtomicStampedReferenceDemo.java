package com.common.concurrent.cas;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * Created by zhoucg on 2019-03-24.
 * AtomicStampedReference类型使用
 */
public class AtomicStampedReferenceDemo {

    static AtomicStampedReference<Integer> money = new AtomicStampedReference<>(19,0);

    public static void main(String[] args) {
        //模拟多线程通知更新后台数据，进行充值操作
        for(int i=0;i<3;i++) {
            final int timestamp = money.getStamp();
            new Thread(){
                @Override
                public void run() {
                    while (true) {
                        while (true) {
                            Integer m = money.getReference();
                            if(m<20) {
                                if(money.compareAndSet(m,m+20,timestamp,timestamp+1)) {
                                    System.out.println("当前系统余额小于20，进行充值，余额为："+money.getReference()+"元");
                                    break;
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                }
            }.start();
        }

        new Thread(){
            @Override
            public void run() {
                for(int i=0;i<100;i++) {
                    while(true) {
                        int timestamp = money.getStamp();
                        Integer m = money.getReference();
                        if(m>10) {
                            System.out.println("大于10元");
                            if(money.compareAndSet(m,m-10,timestamp,timestamp+1)){
                                System.out.println("成功消费10元");
                                break;
                            }
                        } else {
                            System.out.println("没有足够的金额");
                            break;
                        }
                    }
                }
            }
        }.start();

    }
}
