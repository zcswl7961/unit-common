package com.zcswl.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author zhoucg
 * @date 2019-11-02 0:00
 */
@Configuration
public class EnableAuto {

    @Bean
    public Hello getHello() {
        return new Hello();
    }


    private static class Hello {

        private String name;
        private String value;
    }
}
