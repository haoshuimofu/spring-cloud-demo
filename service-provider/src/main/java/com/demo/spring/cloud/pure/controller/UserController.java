package com.demo.spring.cloud.pure.controller;

import com.demo.spring.cloud.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wude
 * @date 2020/8/19 10:40
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/get")
    public ResponseEntity getUserInfo(@RequestParam("id") String id) {
        String user = null;
        user.trim();
        return ResponseEntity.success("用户已查询到!");
    }


}