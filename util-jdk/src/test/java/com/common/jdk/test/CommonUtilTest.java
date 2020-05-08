package com.common.jdk.test;

import com.common.jdk.CommonUtil;
import org.junit.Test;
import org.springframework.util.Assert;

/**
 * @author zhoucg
 * @date 2020-04-15 10:24
 */
public class CommonUtilTest {

    @Test
    public void test () {
        Assert.isTrue(CommonUtil.isPowerOfTwo(2),"powerOfTwo test");
    }


    @Test
    public void math() {

    }
}
