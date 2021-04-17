package com.example.tuberculosis.controller;

import com.example.tuberculosis.base.PageResult;
import com.example.tuberculosis.base.Paging;
import com.example.tuberculosis.base.ResponseResult;
import com.example.tuberculosis.domain.dto.TuberImageForm;
import com.example.tuberculosis.domain.entity.User;
import com.example.tuberculosis.domain.enums.BaseValue;
import com.example.tuberculosis.service.TuberImageService;
import com.example.tuberculosis.utils.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author xhzhao
 */
@Validated
@RestController
@RequestMapping("/image")
@Api(description= "CT图片接口")
public class TuberImageController {

    @Autowired
    private TuberImageService tuberImageService;

    @ApiOperation(value = "图片上传", produces = "application/json;charset=utf-8")
    @PostMapping(value = "/upload",consumes = "multipart/form-data")
    public ResponseResult<Void>  createOrUpdate(@RequestParam MultipartFile file, @RequestParam @NotNull Integer patientNum,HttpServletRequest request,@RequestParam String diagnosisRecord) throws IOException {
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        tuberImageService.createOrUpdate(kUser.getId(), patientNum, file,diagnosisRecord);
        return new ResponseResult(true,"请求成功");
    }

    @ApiOperation(value = "智能分析")
    @PostMapping("run/{id}")
    public ResponseResult<List<Double>> runModel(@PathVariable Integer id) {
        List<Double> doubles = new ArrayList<>();
        doubles.add(0.6);
        doubles.add(0.3);
        doubles.add(0.1);
        return new ResponseResult(true,"请求成功",doubles);
    }

    @ApiOperation(value = "查看图片详情", produces = "application/json;charset=utf-8")
    @GetMapping("/url/{id}")
    public ResponseResult<Void> getImageUrls(@PathVariable(name="id")Integer id,HttpServletRequest request) {
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        List<BaseValue> urlList= tuberImageService.getRecord(id);
        return new ResponseResult(true,"请求成功",urlList);
    }

    @ApiOperation(value = "删除图片", produces = "application/json;charset=utf-8")
    @DeleteMapping("/deleteImage/{id}")
    public ResponseResult<Void>  deleteImage(@PathVariable(name="id")Integer id,HttpServletRequest request) {
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        tuberImageService.deleteImage(id);
        return new ResponseResult(true,"请求成功");
    }

    @PutMapping("/updateImage")
    @ApiOperation(value = "编辑CT图像诊断", produces = "application/json;charset=utf-8")
    public  ResponseResult<Void> updatePatient(@RequestBody TuberImageForm.UpdateTuberImage updateTuberImage, HttpServletRequest request) {
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
       tuberImageService.updateTuberImage(updateTuberImage);
        return new ResponseResult(true, "请求成功");
    }

    @GetMapping("/getTuberImageList")
    @ApiOperation(value = "获取CT图片列表", produces = "application/json;charset=utf-8")
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
    public ResponseResult<TuberImageForm.User> getList(Paging paging, @RequestParam(required = false,name = "queryString") List<String> queryString, @RequestParam(required = false, name = "orderBy") List<String> orderBys, HttpServletRequest request) {
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        if (CollectionUtils.isEmpty((Collection) queryString)) {
            queryString = new ArrayList();
        }
        if (CollectionUtils.isEmpty((Collection) orderBys)) {
            orderBys = new ArrayList();
            ((List) orderBys).add("createTimeDesc");
        }
        PageResult<TuberImageForm.User> tuberUser = tuberImageService.getList(paging,queryString,orderBys,kUser);
        return new ResponseResult(true,"请求成功",tuberUser);
    }

    @GetMapping("/getTuberImageDetail/{id}")
    @ApiOperation(value = "查询图片病人详情", produces = "application/json;charset=utf-8")
    public  ResponseResult<TuberImageForm.User> getUserDetail(@PathVariable(name="id") Integer id, HttpServletRequest request) {
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        TuberImageForm.User tuberImage = tuberImageService.getDetail(id);
        return new ResponseResult(true,"请求成功",tuberImage);
    }
}
