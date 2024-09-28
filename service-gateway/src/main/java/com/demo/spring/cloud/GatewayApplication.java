package com.demo.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

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

    @Component
    public class MyGlobalFilter implements GlobalFilter, Ordered {
        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            System.out.println("服务调用请求：" + exchange.getRequest().getURI());
            return chain.filter(exchange).then(Mono.fromRunnable(() ->
                    System.out.println("服务调用返回了，" + exchange.getResponse().getStatusCode())));
        }
        @Override
        public int getOrder() {
            return 0;
        }
    }

}