package com.example.tuberculosis.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Created by shihuajie on 2019-08-08
 */
@Configuration
public class DataSourcesConfig {

    @Bean("primaryDataSource")
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.druid.primary")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

}