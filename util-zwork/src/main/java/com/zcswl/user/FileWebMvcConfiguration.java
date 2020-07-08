package com.zcswl.user;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author zhoucg
 * @date 2020-06-20 23:53
 */
@Configuration
public class FileWebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //addResourceHandler是指你想在url请求的路径

        //addResourceLocations是图片存放的真实路径

        registry.addResourceHandler("/file/2020/06/**").addResourceLocations("file:D:/zuihou/file/data/2020/06/");
        super.addResourceHandlers(registry);
    }
}
