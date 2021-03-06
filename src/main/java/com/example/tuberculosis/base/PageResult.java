package com.example.tuberculosis.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页结果集
 * created date：2017-09-01
 *
 * @author niezhegang
 */
@Getter
@Setter
@ApiModel(description = "分页结果")
public class PageResult<T> {
    //总记录数
    @ApiModelProperty(value = "总记录数", required = true, readOnly = true)
    private long total;
    //当前页
    @ApiModelProperty(value = "当前页码", required = true, readOnly = true)
    private int pageIndex;
    //页记录数
    @ApiModelProperty(value = "每页记录数", required = true, readOnly = true)
    private int limit;
    //结果行
    @ApiModelProperty(value = "结果集内容", required = true, readOnly = true)
    private List<T> content = new ArrayList<>();
}
