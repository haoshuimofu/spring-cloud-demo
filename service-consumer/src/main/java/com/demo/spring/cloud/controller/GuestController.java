package com.demo.spring.cloud.controller;

import com.demo.spring.cloud.ResponseEntity;
import com.demo.spring.cloud.client.HelloApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer/pass")
public class GuestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private HelloApiClient helloApiClient;

    @GetMapping("/say")
    public ResponseEntity<String> sayHello(String name) {
        return ResponseEntity.success("welcome, you are a guest!");
    }

}
