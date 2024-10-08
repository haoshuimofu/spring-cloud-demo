package com.demo.spring.cloud.controller;

import com.demo.spring.cloud.ResponseEntity;
import com.demo.spring.cloud.api.HelloApi;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wude
 * @date 2020/4/29 13:37
 */
@RestController
public class HelloApiController implements HelloApi {

    @Override
    public ResponseEntity<String> sayHello(String name) {
        // 线程sleep模拟超时熔断
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 服务端直接抛异常，测试异常是否传递
//        System.err.println(1 / 0);
        return ResponseEntity.success("Hello, " + name);
    }
}