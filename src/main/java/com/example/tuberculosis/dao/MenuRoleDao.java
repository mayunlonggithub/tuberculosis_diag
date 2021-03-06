package com.example.tuberculosis.dao;

import com.example.tuberculosis.base.CustomRepostory;
import com.example.tuberculosis.domain.entity.MenuRole;

import java.util.List;

/**
 * @author Ma
 * @version 1.0
 * @date 2020/1/11 22:57
 */
public interface MenuRoleDao extends CustomRepostory<MenuRole,Integer> {
    public List<MenuRole> findByMenuId(Integer menuId);
}
