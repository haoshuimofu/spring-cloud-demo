package com.demo.spring.cloud.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CustomedGlobalFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        System.out.println("[Gateway] path=" + path);
        return chain.filter(exchange)
                .onErrorResume(ex -> {
                    System.out.println("[Gateway][" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + "]" + path + " 出错了!");
                    ex.printStackTrace();
                    return Mono.error(ex);
                })
                .then(Mono.fromRunnable(() ->
                        System.out.println("服务调用返回了，" + exchange.getResponse().getStatusCode())));
    }

}
