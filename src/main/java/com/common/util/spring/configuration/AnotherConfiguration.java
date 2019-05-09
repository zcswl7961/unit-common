package com.common.util.spring.configuration;

import com.common.util.spring.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: zhoucg
 * @date: 2019-05-09
 */
@Configuration
public class AnotherConfiguration {

    @Bean
    public User anotherUser() {
        return new User();
    }
}
