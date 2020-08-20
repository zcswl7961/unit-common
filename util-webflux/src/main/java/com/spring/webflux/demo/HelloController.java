package com.spring.webflux.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author zhoucg
 * @date 2020-07-27 20:19
 */
@Slf4j
@RestController
@RequestMapping("/api/")
public class HelloController {

    @GetMapping("mono")
    public Mono<String> mono() {
        System.out.println("测试");
        return Mono.just("hello webflux");
    }


    @GetMapping("mono/object")
    public Mono<Object> monoObject() {
        return Mono.create(monoSink -> {
            log.info("创建 Mono");
            monoSink.success("hello webflux");
        }) .doOnSubscribe(subscription -> { //当订阅者去订阅发布者的时候，该方法会调用
                    log.info("{}",subscription);
                }).doOnNext(o -> { //当订阅者收到数据时，改方法会调用
                    log.info("{}",o);
                });
    }
}
