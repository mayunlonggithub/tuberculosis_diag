package com.example.tuberculosis.controller;

import com.example.tuberculosis.base.ResponseResult;
import com.example.tuberculosis.domain.entity.User;
import com.example.tuberculosis.domain.enums.BaseValue;
import com.example.tuberculosis.service.UserService;
import com.example.tuberculosis.utils.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @GetMapping("/getUser/{roleType}")
    @ApiOperation(value = "获取所有病人账户", produces = "application/json;charset=utf-8")
    public ResponseResult<List<BaseValue>> getUser(HttpServletRequest request, @PathVariable(name="roleType") Integer roleType) {
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        List<BaseValue> bList = new ArrayList<>();
        for(User user:userService.getUser(roleType)){
            BaseValue baseValue=new BaseValue();
            baseValue.setKey(String.valueOf(user.getId()));
            baseValue.setValue(user.getAccount());
            bList.add(baseValue);
        }
        return new ResponseResult(true, "请求成功", bList);
    }
}
