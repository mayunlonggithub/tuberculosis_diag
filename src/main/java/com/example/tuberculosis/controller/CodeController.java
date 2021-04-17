package com.example.tuberculosis.controller;

import com.example.tuberculosis.base.ResponseResult;
import com.example.tuberculosis.domain.dto.UserForm;
import com.example.tuberculosis.domain.entity.PatientUser;
import com.example.tuberculosis.domain.entity.User;
import com.example.tuberculosis.service.PatientInfoService;
import com.example.tuberculosis.service.UserService;
import com.example.tuberculosis.utils.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ma
 * @version 1.0
 * @date 2020/1/4 13:00
 */
@RestController

@Api(description = "字典表管理")
@RequestMapping("/cde")
public class CodeController {
    @Autowired
    private UserService userService;
    @Autowired
    private PatientInfoService patientInfoService;

    @GetMapping("/getUser")
    @ApiOperation(value = "获取所有病人账户", produces = "application/json;charset=utf-8")
    public ResponseResult<List<UserForm.UserPatient>> getUser(HttpServletRequest request) {
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        List<UserForm.UserPatient> bList = new ArrayList<>();
        for(User user:userService.getUser(Constant.PATIENT)){
            UserForm.UserPatient userPatient = new UserForm.UserPatient();
            userPatient.setId(user.getId());
            userPatient.setAccount(user.getAccount());
            userPatient.setNickname(user.getNickname());
            bList.add(userPatient);
        }
        return new ResponseResult(true, "请求成功", bList);
    }

    @GetMapping("/getUserPatient")
    @ApiOperation(value = "获取当前医生所添加的病人账户", produces = "application/json;charset=utf-8")
    public ResponseResult<List<UserForm.UserPatient>> getPatientOfDoctor(HttpServletRequest request) {
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        List<UserForm.UserPatient> bList = new ArrayList<>();
        for(PatientUser user:patientInfoService.getPatentInfo(kUser.getId())){
                    UserForm.UserPatient userPatient = new UserForm.UserPatient();
                    userPatient.setId(user.getPatientNum());
                    User user1 = userService.getUserDetail(user.getPatientNum());
                    userPatient.setAccount(user1.getAccount());
                    userPatient.setNickname(user1.getNickname());
                    bList.add(userPatient);
        }
        return new ResponseResult(true, "请求成功", bList);
    }

}
