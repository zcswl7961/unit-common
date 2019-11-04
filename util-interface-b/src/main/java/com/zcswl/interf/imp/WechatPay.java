package com.zcswl.interf.imp;

import com.google.auto.service.AutoService;
import com.zcswl.inter.Pay;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author zhoucg
 * @date 2019-11-04 15:52
 */
@AutoService(Pay.class)
public class WechatPay implements Pay{
    @Override
    public void pay() {
        System.out.println("微信支付");
    }
}
