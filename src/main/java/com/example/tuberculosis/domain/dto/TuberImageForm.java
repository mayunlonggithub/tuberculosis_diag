package com.example.tuberculosis.domain.dto;

import com.example.tuberculosis.base.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Ma
 * @version 1.0
 * @date 2021/3/5 19:20
 */
public class TuberImageForm {

    @Data
    @ApiModel(value = "tuberImage",description = "CT用户信息")
    public static class User extends BaseBean {

        @ApiModelProperty(value = "CT表id")
        private Integer id;
        @ApiModelProperty(value = "创建时间")
        private Date createTime;
        @ApiModelProperty(value = "工号")
        private String employeeId;
        @ApiModelProperty(value = "用户昵称")
        private String nickname;
        @ApiModelProperty(value = "用户账号")
        private String account;
        @ApiModelProperty(value = "诊疗记录")
        private String diagnosisRecord;
        @ApiModelProperty(value = "诊疗状态")
        private Integer status;
   }

    @Data
    @ApiModel(value = "upadte.tuberImage",description = "修改CT用户信息")
    public static class UpdateTuberImage extends BaseBean {
        @ApiModelProperty(value = "CT表id")
        private Integer id;
        @ApiModelProperty(value = "诊疗记录")
        private String diagnosisRecord;
    }
}
