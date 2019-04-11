package com.common.util.spring.impor;

import com.common.util.spring.People;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by zhoucg on 2019-04-09.
 */
@Configuration
@Import(value = {People.class})
public class ImportConfig {


}
