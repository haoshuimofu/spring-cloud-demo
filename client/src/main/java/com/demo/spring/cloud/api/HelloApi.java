package com.demo.spring.cloud.api;

import com.demo.spring.cloud.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wude
 * @date 2020/4/29 10:57
 */
@RequestMapping("/api/hello")
public interface HelloApi {

    @GetMapping("/say")
    ResponseEntity<String> sayHello(@RequestParam(value = "name", defaultValue = "none") String name);

}