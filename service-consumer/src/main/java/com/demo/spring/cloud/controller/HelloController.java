package com.demo.spring.cloud.controller;

import com.demo.spring.cloud.JsonResult;
import com.demo.spring.cloud.client.HelloApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wude
 * @date 2020/4/29 11:05
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private HelloApiClient helloApiClient;

    @GetMapping("/say")
    public JsonResult<String> sayHello(String name) {
        return helloApiClient.sayHello(name);
    }
}