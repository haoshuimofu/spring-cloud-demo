package com.demo.spring.cloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wude
 * @date 2020/8/19 10:23
 */
@ControllerAdvice
//@ControllerAdvice(basePackages = {"com.demo.spring.cloud.controller"})
public class CommonExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public JsonResult errorHandler(Exception ex) {
        LOGGER.error("### 程序执行出错!", ex);
        return JsonResult.fail(500, "程序内部错误");
    }

}