package com.zcswl.tools.spi;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author zhoucg
 * @date 2019-11-04 15:24
 */
public class AliPay implements Pay{
    @Override
    public void pay() {
        System.out.println("阿里支付");
    }
}
