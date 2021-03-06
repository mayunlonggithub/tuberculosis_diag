package com.example.tuberculosis.base;

import org.dozer.DozerBeanMapper;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * created date：2017-08-30
 * @author niezhegang
 */
@Configuration
public class DozerConfiguration implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean() throws Exception{
        DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean = new DozerBeanMapperFactoryBean();
        dozerBeanMapperFactoryBean.setMappingFiles(applicationContext.getResources("classpath*:/dozer/*mapping.xml"));
        return dozerBeanMapperFactoryBean;
    }

    @Bean
    public DozerBeanMapper dozerBeanMapper() throws Exception{
        DozerBeanMapper dozerBeanMapper = (DozerBeanMapper) dozerBeanMapperFactoryBean().getObject();
        BeanPropertyCopyUtils.init(dozerBeanMapper);
        return dozerBeanMapper;
    }

}