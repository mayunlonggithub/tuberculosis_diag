package com.example.tuberculosis.dao;

import com.example.tuberculosis.base.CustomRepostory;
import com.example.tuberculosis.domain.entity.Role;

import java.util.List;

/**
 * @author Ma
 * @version 1.0
 * @date 2020/1/11 18:47
 */
public interface RoleDao extends CustomRepostory<Role,Integer> {
    public List<Role>  findByIdIn(List<Integer> idList);
}
