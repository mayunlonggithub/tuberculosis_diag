package com.example.tuberculosis.base;

/**
 * 封装HTTP响应结果模型类
 *
 * @created 2014-5-19
 * @author  huanglj
 */
public class ResponseResult<T> extends BaseBean {

    private static final long serialVersionUID = -2830751366652316323L;

    /** 请求是否成功，成功为true，否则为true */
    private boolean success = true;
    /** 响应消息，请求失败必须设置响应消息 */
    private String msg = "请求成功。";
    /** 响应数据 */
    private T data;

    /**
     * 构造方法
     *
     * @created 2014-5-19
     * @author  huanglj
     */
    public ResponseResult() {
    }

    /**
     * 构造方法（仅返回响应消息，没有响应数据，通常用于请求失败的情况）
     *
     * @param success   请求是否成功
     * @param msg      响应消息
     * @created 2014-5-19
     * @author  huanglj
     */
    public ResponseResult(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    /**
     * 构造方法，（请求成功时调用此方法，仅返回响应数据，没有响应消息）
     *
     * @param data  响应数据
     * @created 2014-5-19
     * @author  huanglj
     */
    public ResponseResult(T data) {
        this.data = data;
    }

    /**
     * 构造方法
     *
     * @param success  请求是否成功
     * @param msg  响应消息
     * @param data     响应数据
     * @created 2014-5-19
     * @author  huanglj
     */
    public ResponseResult(boolean success, String msg, T data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
