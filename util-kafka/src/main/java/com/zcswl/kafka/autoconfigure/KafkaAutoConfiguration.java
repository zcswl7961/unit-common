package com.zcswl.kafka.autoconfigure;

import com.zcswl.kafka.config.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhoucg
 * @date 2020-06-06 23:19
 */
@Configuration
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaAutoConfiguration {

}
