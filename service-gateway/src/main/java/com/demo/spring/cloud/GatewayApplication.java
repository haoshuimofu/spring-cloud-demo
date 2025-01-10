package com.demo.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wude
 * @date 2020/5/15 10:27
 */
@RestController
@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
public class GatewayApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(GatewayApplication.class, args);
    }


}