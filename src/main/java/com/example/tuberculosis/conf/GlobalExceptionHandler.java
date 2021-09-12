package com.example.tuberculosis.conf;

import com.example.tuberculosis.base.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zr
 * @description 全局异常响应
 * @date 25/12/2017
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    /**ss
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 拦截Assert断言
     *
     * @param request 请求
     * @param e       异常
     * @return 响应
     * @throws IllegalArgumentException 抛出Assert断言
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseResult IllegalArgumentException(HttpServletRequest request, RuntimeException e) throws RuntimeException {
        e.printStackTrace();
        logger.error("请求：{}, params:{}, 参数错误：{}", request.getRequestURI(), request.getParameterMap().toString(), e);
        return new ResponseResult(false,e.getMessage());
    }

}
