package com.demo.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;

/**
 * @author wude
 * @date 2020/4/24 14:01
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
//@EnableFeignClients
public class ServiceProviderOtherApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ServiceProviderOtherApplication.class, args);
    }

}