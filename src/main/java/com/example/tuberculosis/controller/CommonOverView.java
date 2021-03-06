package com.example.tuberculosis.controller;

import com.example.tuberculosis.base.ResponseResult;
import com.example.tuberculosis.domain.entity.User;
import com.example.tuberculosis.service.TuberImageService;
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

/**
 * @author Ma
 * @version 1.0
 * @date 2020/1/4 15:11
 */
@RestController
@Api(description = "情况总览")
@RequestMapping("/overView")
public class CommonOverView {
    @Autowired
    private UserService userService;
    @Autowired
    private TuberImageService tuberImageService;
    @GetMapping("/getAllDoctor")
    @ApiOperation(value = "获取所有医生的数量", produces = "application/json;charset=utf-8")
    public ResponseResult<Integer> getAllDoctor(HttpServletRequest request) {
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser, "未登录或登录已失效，请重新登录");
        Integer doctorSize = userService.getAllDoctor();
        return new ResponseResult<>(true, "请求成功", doctorSize);
    }

    @GetMapping("/getAllPatient")
    @ApiOperation(value = "获取所有病人的数量", produces = "application/json;charset=utf-8")
    public ResponseResult<Integer> getAllPatient(HttpServletRequest request) {
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser, "未登录或登录已失效，请重新登录");
        Integer patientSize = userService.getAllPatient();
        return new ResponseResult<>(true, "请求成功", patientSize);
    }

    @GetMapping("/getAllTuberImage")
    @ApiOperation(value = "获取所有CT数量", produces = "application/json;charset=utf-8")
    public ResponseResult<Integer> getAllTuberImage(HttpServletRequest request) {
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser, "未登录或登录已失效，请重新登录");
        Integer tuberImageSize = tuberImageService.getAllTuberImage();
        return new ResponseResult<>(true, "请求成功", tuberImageSize );
    }

    @GetMapping("/getAllAuditDoctor")
    @ApiOperation(value = "获取待审核的医生数量", produces = "application/json;charset=utf-8")
    public ResponseResult<Integer> getAllAuditDoctor(HttpServletRequest request) {
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser, "未登录或登录已失效，请重新登录");
        Integer auditDoctorSize = userService.getAllAuditDoctor();
        return new ResponseResult<>(true, "请求成功", auditDoctorSize);
    }
}

