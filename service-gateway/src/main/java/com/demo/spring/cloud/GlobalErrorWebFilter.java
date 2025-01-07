package com.demo.spring.cloud;

import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Order(-1) // 确保这个过滤器是第一个运行
public class GlobalErrorWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange).onErrorResume(throwable -> {
            throwable.printStackTrace();
            // 检查异常是否是ResponseStatusException
            if (throwable instanceof ResponseStatusException) {
                ResponseStatusException exception = (ResponseStatusException) throwable;
                HttpStatus status = exception.getStatus();
                ServerHttpResponse response = exchange.getResponse();

                // 设置响应状态和内容
                response.setStatusCode(status);
                response.getHeaders().set("Content-Type", "application/json");

                // 返回自定义错误信息
                return response.writeWith(Mono.fromSupplier(() -> {
                    DataBufferFactory bufferFactory = response.bufferFactory();
                    // 这里可以返回自定义的错误信息，比如使用JSON格式
                    return bufferFactory.wrap(("{\"message\": \"" + exception.getReason() + "\"}".getBytes()).getBytes());
                }));
            }

            // 如果不是ResponseStatusException异常，则重新抛出
            return Mono.error(throwable);
        });
    }
}