package com.zcswl.tools.spi;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author zhoucg
 * @date 2019-11-04 15:25
 */
public class WechatPay implements Pay{
    @Override
    public void pay() {
        System.out.println("微信支付");
    }
}
