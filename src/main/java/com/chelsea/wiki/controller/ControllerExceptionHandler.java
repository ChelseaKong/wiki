package com.chelsea.wiki.controller;

import com.chelsea.wiki.resp.CommonResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice // 对Controller做一些统一异常处理、数据的预处理等
public class ControllerExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    // 校验异常统一处理
    // @param e
    // @return
    @ExceptionHandler(value = BindException.class) // 对BindException做一个统一的异常处理，这是有针对性的
    // @ExceptionHandler(value = Exception.class) 所有的异常
    @ResponseBody
    public CommonResp validExceptionHandler(BindException e) {
        CommonResp commonResp = new CommonResp();
        LOG.warn("参数校验失败 - Parameter validation failed: {}", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        commonResp.setSuccess(false);
        commonResp.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return commonResp;
    }
}
