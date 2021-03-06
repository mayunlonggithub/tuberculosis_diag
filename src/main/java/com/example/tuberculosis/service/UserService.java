package com.example.tuberculosis.service;

import com.example.tuberculosis.base.PageResult;
import com.example.tuberculosis.base.Paging;
import com.example.tuberculosis.domain.dto.UserForm;
import com.example.tuberculosis.domain.entity.User;

import java.util.List;

/**
 * jackson 相关配置
 *
 * @author J on 20191107.
 */
public interface UserService {

    /**
     * @Title login
     * @Description 登陆
     * @param userLogin 用户信息对象
     * @return
     * @return KUser
     */
    public User login(UserForm.UserLogin userLogin);


    /**
     * @Title getList
     * @Description 获取用户分页列表
     * @return
     */
    public PageResult<User> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer queryType,String account);

//    public PageResult<DoctorUser> getDoctorUserList(Paging paging, List<String> queryString, List<String> orderBys);

    /**
     * @Title delete
     * @Description 删除用户
     * @param uId 用户ID
     * @return void
     */
    public void delete(Integer id);

    /**
     * @Title addUser
     * @Description 插入一个用户
     * @param addTUser
     * @return void
     */
    public void addUser(UserForm.AddUser addTUser,Integer roleType);


    /**
     * @Title update
     * @Description 更新用户
     * @param updateUser 用户对象
     * @param uId 用户ID
     * @return void
     */
    public void updateUser(UserForm.UpdateUser updateUser,Integer uId);

    /**
     * @Title isAdmin
     * @Description 用户是否为管理员
     * @param uId 用户ID
     * @return
     * @return boolean
     */
    public boolean isAdmin(Integer uId);

    public List<User> getUser(Integer roleType);

    public Integer getAllDoctor();

    public Integer getAllPatient();

    public Integer getAllAuditDoctor();

    public void auditAccount(Integer accessFlag,Integer doctorUserId);

    public User getUserDetail(Integer id);

}
