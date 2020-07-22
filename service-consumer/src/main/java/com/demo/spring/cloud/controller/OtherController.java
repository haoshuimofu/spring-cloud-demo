package com.demo.spring.cloud.controller;

import com.demo.spring.cloud.JsonResult;
import com.demo.spring.cloud.client.OtherApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wude
 * @date 2020/4/29 15:50
 */
@RestController
@RequestMapping("other")
public class OtherController {

    @Autowired
    private OtherApiClient otherApiClient;

    @GetMapping("do")
    public JsonResult doSomething() {
        return otherApiClient.doSomething();
    }
}