package com.example.tuberculosis.conf;

/**
 * @author Ma
 * @version 1.0
 * @date 2020/1/5 11:40
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 资源映射路径
 */
@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        int b=2;
        registry.addResourceHandler("/image/**").addResourceLocations("file:C:/users/administrator/data/image/");
    }
}s
    