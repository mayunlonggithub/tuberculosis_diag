package com.example.tuberculosis;

import com.example.tuberculosis.common.FileAccessProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.MultipartConfigElement;

@EnableSwagger2
@SpringBootApplication
@EnableTransactionManagement
@EnableConfigurationProperties({FileAccessProperties.class})
public class TuberculosisApplication {
    public static void main(String[] args) {
        SpringApplication.run(TuberculosisApplication.class, args);
    }
    /**
     * 文件上传配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大
        factory.setMaxFileSize("50240KB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("50240KB");
        return factory.createMultipartConfig();
    }
}
