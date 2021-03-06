package com.example.tuberculosis.base;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * created date：2017-08-31
 *
 * @author niezhegang
 */
public abstract class PageUtils {

    private static final String desc = "desc";
    private static final String asc = "asc";

    public static Pageable transform(Paging paging){
        PageRequest pageRequest = null;
        if(paging != null) {
            pageRequest = new PageRequest(paging.getPageIndex() - 1,paging.getLimit());
        }
        return pageRequest;
    }

    public static Pageable transform(Paging paging , List<String> orderBys){
        PageRequest pageRequest = null;
        List<Sort.Order> orders = new ArrayList<>();

        if(CollectionUtils.isNotEmpty(orderBys)){
            Sort.Order order = null;
            String direction = null;
            //orderby的格式为
            for(String orderBy : orderBys) {
                Assert.hasText(orderBy,"排序字符串不能为空！");
                orderBy = StringUtils.trim(orderBy);
                int index = StringUtils.lastIndexOfIgnoreCase(orderBy,desc);
                if(index < 0 ){
                    index = StringUtils.lastIndexOfIgnoreCase(orderBy,asc);
                }
                //未搜索到
                if(index < 0){
                    orders.add(new Sort.Order(orderBy));
                }
                else {
                    direction = orderBy.substring(index);
                    if(StringUtils.equalsIgnoreCase(direction,desc)){
                        orders.add(new Sort.Order(Sort.Direction.DESC,orderBy.substring(0,index)));
                    }
                    else{
                        orders.add(new Sort.Order(Sort.Direction.ASC,orderBy.substring(0,index)));
                    }
                }
            }
        }
        if(paging != null) {
            if(CollectionUtils.isNotEmpty(orders)){
                pageRequest = new PageRequest(paging.getPageIndex() - 1,paging.getLimit(),new Sort(orders));
            }
            else {
                pageRequest = new PageRequest(paging.getPageIndex() - 1,paging.getLimit());
            }
        }
        return pageRequest;
    }

    public static <T> PageResult<T> transformPageResult(Page<T> page, Paging paging){
        PageResult<T> pageResult = new PageResult<>();
        if(page != null){
            pageResult.setContent(page.getContent());
            pageResult.setTotal(page.getTotalElements());
            pageResult.setLimit(paging.getLimit());
            pageResult.setPageIndex(paging.getPageIndex());
        }
        return pageResult;
    }

    /**
     * 分页结果集内容复制为dto内容输出
     * @param source
     * @param targetClass
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S,T> PageResult<T> copyPageResult(PageResult<S> source,Class<T> targetClass){
        Assert.notNull(targetClass,"分页结果的target类型不能为空！");
        PageResult<T> ret = null;
        if(source != null){
            ret = new PageResult<>();
            BeanPropertyCopyUtils.copy(source,ret);
            ret.setContent(BeanPropertyCopyUtils.copy(source.getContent(), targetClass));
        }
        return ret;
    }

}

