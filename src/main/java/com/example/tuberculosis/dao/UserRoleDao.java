package com.example.tuberculosis.dao;

import com.example.tuberculosis.base.CustomRepostory;
import com.example.tuberculosis.domain.entity.UserRole;

import java.util.List;

/**
 * @author Ma
 * @version 1.0
 * @date 2020/1/1 14:09
 */
public interface UserRoleDao extends CustomRepostory<UserRole,Integer> {
    public List<UserRole> findByUserId(Integer userId);

}
