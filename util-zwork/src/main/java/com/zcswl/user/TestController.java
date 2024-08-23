package com.zcswl.user;

import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * @author zhoucg
 * @date 2020-10-30 17:39
 */
@RestController
public class TestController {


    @GetMapping("/test")
    public String test() throws InterruptedException {
        return "1";
    }

    @PostMapping("/postTest")
    public String testXinyig(@RequestBody Demo Demo) {
        return Demo.getAddress() + ":" + Demo.getName();
    }

    @PostMapping("/postNo")
    public String xingyi() throws InterruptedException {
        Random random = new Random();
        int i = random.nextInt(5000);
        System.out.println((long)i);
       Thread.sleep(5000L);
        return "xingyi";
    }


    @PostMapping("/postArray")
    public String testArray(@RequestBody List<Demo> demos) {
        return demos.get(0).getAddress() + ":" + demos.size();
    }

    @PostMapping("/postArray1")
    public String testArray1(@RequestBody Tests tests) {
        return tests.getDemos().get(0).getAddress() + ":" + tests.getDemos().size();
    }
}
