package com.demo.spring.cloud.api;

import com.demo.spring.cloud.JsonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wude
 * @date 2020/4/29 15:41
 */
@RequestMapping("other")
public interface OtherApi {

    @GetMapping("doSomething")
    JsonResult doSomething();

}