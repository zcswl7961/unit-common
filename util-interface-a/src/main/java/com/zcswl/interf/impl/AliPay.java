package com.zcswl.interf.impl;

import com.google.auto.service.AutoService;
import com.zcswl.inter.Pay;

/**
 * <p>
 * 实现
 * </p>
 *
 * @author zhoucg
 * @date 2019-11-04 15:45
 */
@AutoService(Pay.class)
public class AliPay implements Pay{
    @Override
    public void pay() {
        System.out.println("支付宝支付");
    }
}
