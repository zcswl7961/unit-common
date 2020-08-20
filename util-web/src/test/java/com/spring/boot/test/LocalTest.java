package com.spring.boot.test;

import com.spring.boot.web.ApplicationDemo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zhoucg
 * @date 2020-07-23 16:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationDemo.class)
public class LocalTest {

    @Test
    public void test() {

        System.out.println("================");
    }
}
