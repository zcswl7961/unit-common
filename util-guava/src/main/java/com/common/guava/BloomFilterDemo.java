package com.common.guava;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;

/**
 * BloomFilter
 * https://www.jianshu.com/p/bef2ec1c361f
 * 算法讲解
 * @author zhoucg
 * @date 2021-01-05 10:14
 */
public class BloomFilterDemo {

    public static void main(String[] args) {

        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), 1000, 0.00001);
        bloomFilter.put("zhoucg");

    }
}
