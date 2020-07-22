package com.demo.spring.cloud.controller;

import com.demo.spring.cloud.JsonResult;
import com.demo.spring.cloud.api.HelloApi;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wude
 * @date 2020/4/29 13:37
 */
@RestController
public class HelloApiController implements HelloApi {

    @Override
    public JsonResult<String> sayHello(String name) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return JsonResult.success("Hello, " + name);
    }
}