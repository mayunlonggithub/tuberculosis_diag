package com.example.tuberculosis.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

/**
 * Created by shihuajie on 2019-08-08
 */
public interface CustomRepostory<T,ID extends Serializable> extends JpaRepository<T,ID>, JpaSpecificationExecutor<T> {

    public PageResult<T> findAll(Paging paging, List<String> queryString, List<String> orderBy);

    public EntityManager getEntityManager();


}
