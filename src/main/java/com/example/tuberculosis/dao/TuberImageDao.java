package com.example.tuberculosis.dao;

import com.example.tuberculosis.base.CustomRepostory;
import com.example.tuberculosis.domain.entity.TuberImage;
import com.example.tuberculosis.domain.entity.User;

import java.util.List;

/**
 * @author Ma
 * @version 1.0
 * @date 2019/12/29 14:58
 */
public interface TuberImageDao extends CustomRepostory<TuberImage,Integer> {
       List<TuberImage> findByDelFlag(Integer delFlag);
}
