package com.example.tuberculosis.base;

import lombok.Getter;
import lombok.Setter;

/**
 * 封装分页参数模型类
 */
@Setter
@Getter
public class Paging extends BaseBean {
    private static final long serialVersionUID = -5871263750693828476L;

    public static final int LIMIT_NO_PAGING = Integer.MAX_VALUE;

//	/** 分页起始行，默认为1，表示从第一条记录开始查询，查询全部记录  */
//	private int start = 1;
    /**
     * 当前第几页，从1开始，用于前端查询传入参数
     */
    private int pageIndex = 1;
    /**
     * 每页显示行数，表示不分页，查询全部记录
     */
    private int limit = LIMIT_NO_PAGING;

    public int getStart() {
        return (pageIndex - 1) * limit;
    }

    public void setStart(int start) {
        throw new IllegalArgumentException("start自动由pageIndex计算生成，不提供设置功能！");
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        if (pageIndex <= 0) {
            throw new IllegalArgumentException("当前查询页参数<=0");
        }
        this.pageIndex = pageIndex;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("当前分页行数<=0");
        }
        this.limit = limit;
    }
}
