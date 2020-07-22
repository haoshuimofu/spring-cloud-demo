package com.demo.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

/**
 * @author wude
 * @date 2020/4/24 14:01
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker
public class ServiceConsumerApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ServiceConsumerApplication.class, args);

        System.err.println(ctx.getBean(HelloApiClientFallbackFactory.class) == null);
    }

}