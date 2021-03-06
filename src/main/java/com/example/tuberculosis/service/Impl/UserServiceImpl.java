package com.example.tuberculosis.service.Impl;

import com.example.tuberculosis.base.BeanPropertyCopyUtils;
import com.example.tuberculosis.base.PageResult;
import com.example.tuberculosis.base.Paging;
import com.example.tuberculosis.dao.RoleDao;
import com.example.tuberculosis.dao.UserDao;
import com.example.tuberculosis.dao.UserRoleDao;
import com.example.tuberculosis.domain.dto.UserForm;
import com.example.tuberculosis.domain.entity.User;
import com.example.tuberculosis.domain.entity.UserRole;
import com.example.tuberculosis.service.UserService;
import com.example.tuberculosis.utils.Constant;
import com.example.tuberculosis.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;


/**
 * jackson 相关配置
 *
 * @author J on 20191107.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private RoleDao roleDao;
    /**
     * @Title login
     * @Description 登陆
     * @param userLogin 用户信息对象
     * @return
     * @return KUser
     */
    @Override
    public User login(UserForm.UserLogin userLogin){
        User user = userDao.findByAccountAndDelFlagAndIfAccess(userLogin.getAccount(),1,1);
        if (null != user){
            if (user.getPassword().equals(MD5Utils.Encrypt(userLogin.getPassword(), true))){
                return user;
            }
            return null;
        }
        return null;
    }


    /**
     * @Title getList
     * @Description 获取用户分页列表
     * @return
     */
    @Override
    public PageResult<User> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer queryType,String account){
        //未删除
        queryString.add("delFlag~Eq~1");
        if(queryType==Constant.QUERYDOCOTOR) {
            queryString.add("roleType~Eq~2");
            queryString.add("ifAccess~Eq~1");
        }
        if(queryType==Constant.AUDITDOCOTOR) {
            queryString.add("roleType~Eq~2");
            queryString.add("ifAccess~Eq~2");
        }
        if(queryType == Constant.PERSON) {
            queryString.add("account~Eq~"+account);
        }
        PageResult<User> user = userDao.findAll(paging,queryString,orderBys);
        return user;
    }

//    @Override
//    public PageResult<DoctorUser> getDoctorUserList(Paging paging, List<String> queryString, List<String> orderBys){
//        //未删除
//        queryString.add("ifAccess~Eq~2");
//        //根据角色查询
//        PageResult<DoctorUser> doctorUser = doctorUserDao.findAll(paging,queryString,orderBys);
//        return doctorUser;
//    }

    /**
     * @Title delete
     * @Description 删除用户
     * @return void
     */
    @Override
    @Transactional
    public void delete(Integer id) {
        Optional<User> userOptional = userDao.findById(id);
        User user = new User();
        if (userOptional.isPresent()) {
            user = userOptional.get();
        }
        Assert.notNull(user, "该用户不存在或已删除");
        if (isAdmin(id)) {
            Assert.isNull(user, "管理员账号不允许删除");
        }
        user.setDelFlag(0);
        userDao.save(user);
//        List<UserRole> userRoleList = userRoleDao.findByUserId(id);
//        for (UserRole userRole : userRoleList) {
//            userRoleDao.delete(userRole);
//        }
    }

    /**
     * @Title addUser
     * @Description 插入一个用户
     * @param addTUser
     * @return void
     */
    @Override
    @Transactional
    public void addUser(UserForm.AddUser addTUser,Integer roleType){
        User user = userDao.findByAccountAndDelFlagAndIfAccess(addTUser.getAccount(),1,1);
        Assert.isNull(user,"该账号已存在");
        if(roleType==Constant.DOCTOR){
            user = BeanPropertyCopyUtils.copy(addTUser,User.class);
            user.setIfAccess(Constant.UNAUDIT);
            user.setPassword(MD5Utils.Encrypt(user.getPassword(), true));
            user.setRoleType(roleType);
            user.setDelFlag(1);
            userDao.save(user);
        }
        if(roleType==Constant.PATIENT || roleType==Constant.ADMIN){
            user = BeanPropertyCopyUtils.copy(addTUser,User.class);
            user.setRoleType(roleType);
            user.setPassword(MD5Utils.Encrypt(user.getPassword(), true));
            user.setDelFlag(1);
            userDao.save(user);
            //加入到用户角色表中
//            Integer userId=user.getId();
//            Integer roleId=user.getRoleType();
//            UserRole userRole=new UserRole();
//            userRole.setUserId(userId);
//            userRole.setRoleId(roleId);
//            userRoleDao.save(userRole);
        }
    }

    /**
     * @Title updateUser
     * @Description 更新用户
     * @param updateUser 用户对象
     * @param uId 用户ID
     * @return void
     */
    @Override
    @Transactional
    public void updateUser(UserForm.UpdateUser updateUser,Integer uId){
        Assert.notNull(uId,"未登录,请重新登录");
        Optional<User> opUser=userDao.findById(updateUser.getId());
        User user=opUser.get();
        user.setEmail(updateUser.getEmail());
        user.setPhone(updateUser.getPhone());
        if(updateUser.getPassword()!=null) {
            user.setPassword(MD5Utils.Encrypt(user.getPassword(), true));
        }
        if(updateUser.getAge()!=null) {
            user.setAge(updateUser.getAge());
        }
        //只有不为null的字段才参与更新
        userDao.save(user);
    }
    /**
     * @Title isAdmin
     * @Description 用户是否为管理员
     * @param uId 用户ID
     * @return
     * @return boolean
     */
    @Override
    public boolean isAdmin(Integer uId){
        Assert.notNull(uId,"未登录,请重新登录");
        Optional<User> userOptional = userDao.findById(uId);
        User user = new User();
        if(userOptional.isPresent()){
            user=userOptional.get();
        }
        Assert.notNull(user,"管理员不存在或已删除");
        if (user.getRoleType() == Constant.ADMIN){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<User> getUser(Integer roleType){
         return  userDao.findByRoleTypeAndDelFlagAndIfAccess(roleType,1,1);
    }

    @Override
    public Integer getAllDoctor(){
        List<User> doctorList=userDao.findByRoleTypeAndDelFlagAndIfAccess(Constant.DOCTOR,1,1);
        return   doctorList.size();
    }

    @Override
    public Integer getAllPatient(){
        List<User> patientList=userDao.findByRoleTypeAndDelFlagAndIfAccess(Constant.PATIENT,1,1);
        return   patientList.size();
    }

    public Integer getAllAuditDoctor() {
        List<User> auditDoctorList=userDao.findByRoleTypeAndDelFlagAndIfAccess(Constant.DOCTOR,1,2);
        return  auditDoctorList.size();
    }

    @Override
    public void auditAccount(Integer accessFlag,Integer doctorUserId) {
        Optional<User> OpDoctorUser = userDao.findById(doctorUserId);
        User doctorUser = OpDoctorUser.get();
        if (accessFlag == 0) {
            doctorUser.setIfAccess(Constant.UNACCESS);
            userDao.save(doctorUser);
        } else {
            User user = BeanPropertyCopyUtils.copy(doctorUser, User.class);
            user.setIfAccess(Constant.ACCESS);
            userDao.save(user);
            //加入到用户角色表中
//            Integer userId=user.getId();
//            Integer roleId=user.getRoleType();
//            UserRole userRole=new UserRole();
//            userRole.setUserId(userId);
//            userRole.setRoleId(roleId);
//            userRoleDao.save(userRole);
//        }
        }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user=userDao.findByAccountAndDelFlag(username,1);
//        if (user == null) {
//            throw new UsernameNotFoundException("账户不存在!");
//        }
//        List<UserRole> userRoleList=userRoleDao.findByUserId(user.getId());
//        List<Integer> roleIdList=new ArrayList<>();
//        for(UserRole userRole:userRoleList){
//            roleIdList.add(userRole.getRoleId());
//        }
//        user.setRoles(roleDao.findByIdIn(roleIdList));
//        return user;
//    }
    }

    public User getUserDetail(Integer id) {
        User user = userDao.getOne(id);
        return user;
    }
}
