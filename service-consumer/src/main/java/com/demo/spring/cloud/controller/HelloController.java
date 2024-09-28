package com.demo.spring.cloud.controller;

import com.demo.spring.cloud.ResponseEntity;
import com.demo.spring.cloud.client.HelloApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private HelloApiClient helloApiClient;

    @GetMapping("/say")
    public ResponseEntity<String> sayHello(String name) {
        try {
            ResponseEntity result = helloApiClient.sayHello(name);
            return result;
        } catch (Exception e) {
            LOGGER.error("### feign异常!", e);
        }
        return ResponseEntity.success("hi, default return!");
    }
}