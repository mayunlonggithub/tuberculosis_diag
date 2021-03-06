package com.example.tuberculosis.domain.dto;

import com.example.tuberculosis.base.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Ma
 * @version 1.0
 * @date 2019/12/22 19:13
 */

public class UserForm {

    @Data
    @ApiModel(value = "userLogin",description = "用户登录")
    public static class UserLogin extends BaseBean {
        @ApiModelProperty(value = "用户账号")
        private String account;
        @ApiModelProperty(value = "用户密码")
        private String password;

    }

    @Data
    @ApiModel(value = "user",description = "用户信息")
    public static class User extends BaseBean {
        @ApiModelProperty(value = "用户ID")
        private Integer id;
        @ApiModelProperty(value = "用户昵称")
        private String nickname;
        @ApiModelProperty(value = "用户邮箱")
        private String email;
        @ApiModelProperty(value = "用户电话")
        private String phone;
        @ApiModelProperty(value = "用户账号")
        private String account;
        @ApiModelProperty(value = "用户密码")
        private String password;
        @ApiModelProperty(value = "创建时间")
        private Date createTime;
        @ApiModelProperty(value = "年龄")
        private Integer age;
        @ApiModelProperty(value = "人员类型")
        private Integer roleType;
        @ApiModelProperty(value = "工号")
        private String employeeId;
        @ApiModelProperty(value = "性别")
        private String gender;
    }

    @Data
    @ApiModel(value = "user.add",description = "新增用户信息")
    public static class AddUser extends BaseBean {
        @ApiModelProperty(value = "用户昵称")
        private String nickname;
        @ApiModelProperty(value = "用户邮箱")
        private String email;
        @ApiModelProperty(value = "用户电话")
        private String phone;
        @ApiModelProperty(value = "用户账号")
        private String account;
        @ApiModelProperty(value = "用户密码")
        private String password;
        @ApiModelProperty(value = "年龄")
        private Integer age;
        @ApiModelProperty(value = "工号")
        private String employeeId;
        @ApiModelProperty(value = "性别")
        private String gender;
    }

    @Data
    @ApiModel(value = "user.update",description = "修改用户信息")
    public static class UpdateUser extends BaseBean {
        @ApiModelProperty(value = "用户表id")
        private Integer id;
        @ApiModelProperty(value = "用户邮箱")
        private String email;
        @ApiModelProperty(value = "用户电话")
        private String phone;
        @ApiModelProperty(value = "年龄")
        private Integer age;
        @ApiModelProperty(value = "密码")
        private String password;

    }
}