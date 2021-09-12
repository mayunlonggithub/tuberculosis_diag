package com.example.tuberculosis.conf;


import com.example.tuberculosis.base.CustomRepostoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

/**
 * 数据服务功能相关jpa配置
 * Created by shihuajie on 2019-08-08
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryPrimary",
        transactionManagerRef = "transactionManagerPrimary",
        basePackages = {"com.example.tuberculosis.dao"},
        repositoryBaseClass= CustomRepostoryImpl.class) //设置Repository所在位置
public class PrimaryJpaConfiguration {

    @Autowired
    @Qualifier("primaryDataSource")
    private DataSource primaryDataSource;
    @Autowired
    private JpaProperties jpaProperties;

    int a =1 ;

    @Primary
    @Bean("entityManagerPrimary")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryPrimary(builder).getObject().createEntityManager();
    }

    @Primary
    @Bean("entityManagerFactoryPrimary")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(primaryDataSource)
                .properties(getVendorProperties())
                .packages("com.example.tuberculosis.domain.entity") //设置实体类所在位置
                .persistenceUnit("primaryPersistenceUnit") //持久化单元名称，当存在多个EntityManagerFactory时，需要制定此名称
                .build();
    }

    private Map getVendorProperties() {
        return jpaProperties.getProperties();
    }

    @Primary
    @Bean("transactionManagerPrimary")
    public PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryPrimary(builder).getObject());
    }
}

