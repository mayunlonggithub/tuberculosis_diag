package com.example.tuberculosis.dao;

import com.example.tuberculosis.base.CustomRepostory;
import com.example.tuberculosis.domain.entity.User;

import java.util.List;

/**
 * @author Ma
 * @version 1.0
 * @date 2019/12/22 19:33
 */
public interface UserDao extends CustomRepostory<User,Integer> {

    public User findByAccountAndDelFlagAndIfAccess(String account, Integer delFlag,Integer ifAccess);
    public List<User> findByRoleTypeAndDelFlagAndIfAccess(Integer roleType,Integer delFlag,Integer ifAccess);

}