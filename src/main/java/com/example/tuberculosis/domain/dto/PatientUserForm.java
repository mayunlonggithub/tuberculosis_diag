package com.example.tuberculosis.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author Ma
 * @version 1.0
 * @date 2019/12/29 21:09
 */
public class PatientUserForm {

    @Data
    @ApiModel(value = "patient",description = "病人信息")
    public static class Patient {
        @ApiModelProperty(value = "病人表id")
        private int id;
        @ApiModelProperty(value = "病人姓名")
        private String nicekName;
        @ApiModelProperty(value = "病人年龄")
        private Integer age;
        @ApiModelProperty(value = "病人性别")
        private String gender;
        @ApiModelProperty(value = "病情描述")
        private String description;
        @ApiModelProperty(value = "病情判断")
        private String judge;
        @ApiModelProperty(value = "接诊医生工号")
        private String employeeId;
        @ApiModelProperty(value = "创建时间")
        private Date createTime;
        @ApiModelProperty(value = "病人email")
        private String email;
        @ApiModelProperty(value = "手机号码")
        private String phone;
        @ApiModelProperty(value = "病人身份证号码")
        private String account;

    }

    @Data
    @ApiModel(value = "patient.add",description = "增加病人信息")
    public static class AddPatient {
        @ApiModelProperty(value = "病情描述")
        private String description;
        @ApiModelProperty(value = "病情判断")
        private String judge;
        @ApiModelProperty(value = "病人账户")
        private String account;
        @ApiModelProperty(value = "病人id")
        private Integer patientNum;
    }

    @Data
    @ApiModel(value = "patient.update",description = "修改病人信息")
    public static class UpdatePatient extends AddPatient {
        @ApiModelProperty(value = "病人信息表id")
        private int id;
        @ApiModelProperty(value = "病情描述")
        private String description;
        @ApiModelProperty(value = "病情判断")
        private String judge;
        @ApiModelProperty(value = "病人email")
        private String email;
        @ApiModelProperty(value = "手机号码")
        private String phone;
    }
    }
