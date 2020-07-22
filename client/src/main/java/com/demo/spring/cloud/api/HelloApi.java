package com.demo.spring.cloud.api;

import com.demo.spring.cloud.JsonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wude
 * @date 2020/4/29 10:57
 */
@RequestMapping("/api")
public interface HelloApi {

    @GetMapping("/sayHello")
    JsonResult<String> sayHello(@RequestParam(value = "name", defaultValue = "none") String name);

}