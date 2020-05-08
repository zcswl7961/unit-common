package com.common.jdk;

/**
 * @author zhoucg
 * @date 2020-04-15 10:22
 */
public final class CommonUtil {

    /**
     * 判断数据是否为偶数
     * @param val value
     * @return boolean
     */
    public static boolean isPowerOfTwo(int val) {
        return (val & -val) == val;
    }





}
