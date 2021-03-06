package com.example.tuberculosis.base;

import com.google.common.collect.FluentIterable;
import org.dozer.DozerBeanMapper;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * created date：2017-09-18
 * @author niezhegang
 */
public abstract class BeanPropertyCopyUtils {

    private static DozerBeanMapper dozerBeanMapper ;

    public static void init(DozerBeanMapper dozerBeanMapper){
        if(BeanPropertyCopyUtils.dozerBeanMapper == null) {
            BeanPropertyCopyUtils.dozerBeanMapper = dozerBeanMapper;
        } else {
            throw new IllegalArgumentException("BeanCopyUtils不允许重复初始化！");
        }
    }

    public static void valid(){
        Assert.notNull(dozerBeanMapper,"BeanCopyUtils未初始化！");
    }

    public static <S,T> T copy(S source, T target){
        valid();
        if(source == null || target == null) {
            return null;
        }
        dozerBeanMapper.map(source,target);
        return target;
    }

    public static <S,T> T copy(S source,Class<T> targetClass){
        valid();
        if(source == null || targetClass == null) {
            return null;
        }
        return dozerBeanMapper.map(source,targetClass);
    }

    public static <S,T> List<T> copy(List<S> source, List<T> target){
        valid();
        if(source == null) {
            return Collections.emptyList();
        }
        dozerBeanMapper.map(source,target);
        return target;
    }

    public static <S,T> List<T> copy(List<S> sources,Class<T> targetClass){
        valid();
        List<T> targets = new ArrayList<>();
        if(sources != null && sources.size() > 0){
            FluentIterable.from(sources).forEach(new Consumer<S>() {
                @Override
                public void accept(S s) {
                    targets.add(copy(s,targetClass));
                }
            });
        }
        return targets;
    }

}
