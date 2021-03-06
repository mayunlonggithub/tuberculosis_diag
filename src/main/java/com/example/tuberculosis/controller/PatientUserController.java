package com.example.tuberculosis.controller;
import com.example.tuberculosis.base.BeanPropertyCopyUtils;
import com.example.tuberculosis.base.PageResult;
import com.example.tuberculosis.base.PageUtils;
import com.example.tuberculosis.base.Paging;
import com.example.tuberculosis.base.ResponseResult;
import com.example.tuberculosis.domain.dto.PatientUserForm;
import com.example.tuberculosis.domain.dto.UserForm;
import com.example.tuberculosis.domain.entity.PatientUser;
import com.example.tuberculosis.domain.entity.User;
import com.example.tuberculosis.service.PatientInfoService;
import com.example.tuberculosis.utils.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 * @author Ma
 * @version 1.0
 * @date 2019/12/29 22:03
 */
@RestController
@Api(description = "病人信息")
@RequestMapping("/patient")
public class PatientUserController {
    @Autowired
    private PatientInfoService patientInfoService;

    @PostMapping("/addPatient")
    @ApiOperation(value = "添加病人", produces = "application/json;charset=utf-8")
    public ResponseResult<Void> addPatient(@RequestBody PatientUserForm.AddPatient addPatient, HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        patientInfoService.addPatientInfo(addPatient, kUser.getId());
        return new ResponseResult(true,"请求成功");
    }

    @DeleteMapping("/deletePatient/{patientId}")
    @ApiOperation(value = "删除病人", produces = "application/json;charset=utf-8")
    public ResponseResult<Void> deletePatient(@PathVariable("patientId") Integer patientId){
        Assert.notNull(patientId,"要删除的策略id不能为空");
        patientInfoService.deletePatientInfo(patientId);
        return new ResponseResult(true,"请求成功");
    }

    @PutMapping("/updatePatient")
    @ApiOperation(value = "修改病人", produces = "application/json;charset=utf-8")
    public  ResponseResult<Void> updatePatient(@RequestBody PatientUserForm.UpdatePatient updatePatient, HttpServletRequest request) {
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        patientInfoService.updatePatientInfo(updatePatient);
        return new ResponseResult(true, "请求成功");
    }

    @GetMapping("/getList")
    @ApiOperation(value = "获取调度策略分页列表", produces = "application/json;charset=utf-8")
    @ApiImplicitParams({@ApiImplicitParam(
            name = "pageIndex",
            value = "分页页码",
            defaultValue = "1",
            dataType = "int",
            paramType = "query"
    ), @ApiImplicitParam(
            name = "limit",
            value = "返回行数",
            defaultValue = "2147483647",
            dataType = "int",
            paramType = "query"
    ), @ApiImplicitParam(
            name = "queryString",
            value = "查询条件",
            defaultValue = "",
            dataType = "String",
            paramType = "query",
            allowMultiple = true
    ), @ApiImplicitParam(
            name = "orderBy",
            value = "排序",
            defaultValue = "",
            dataType = "String",
            paramType = "query",
            allowMultiple = true
    )})
    public ResponseResult<Void> getList(Paging paging, @RequestParam(required = false,name = "queryString") List<String> queryString, @RequestParam(required = false, name = "orderBy") List<String> orderBys, HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        if (CollectionUtils.isEmpty((Collection) queryString)) {
            queryString = new ArrayList();
        }
        if (CollectionUtils.isEmpty((Collection) orderBys)) {
            orderBys = new ArrayList();
            ((List) orderBys).add("createTimeDesc");
        }
        PageResult<PatientUserForm.Patient> patientInf=patientInfoService.getList(paging,queryString,orderBys,kUser);
        return new ResponseResult(true,"请求成功",patientInf);
    }

    @GetMapping("/getPatientDetail/{id}")
    @ApiOperation(value = "查询病人详情", produces = "application/json;charset=utf-8")
    public  ResponseResult<PatientUserForm.Patient> getUserDetail(@PathVariable(name="id") Integer id, HttpServletRequest request) {
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        PatientUserForm.Patient patient = patientInfoService.getDetail(id);
        return new ResponseResult(true,"请求成功",patient);
    }
}
