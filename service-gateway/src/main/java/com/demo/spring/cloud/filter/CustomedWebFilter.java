package com.demo.spring.cloud.filter;

import com.demo.spring.cloud.route.CallbackConfigEntity;
import com.demo.spring.cloud.route.LocalRouteDefinitionRouteLocator;
import com.demo.spring.cloud.utils.URIUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.util.Optional;

/**
 * Spring-web 过滤器, 不会处理spring cloud gateway的异常
 */
@Component
@Order(-1) // 确保这个过滤器是第一个运行
public class CustomedWebFilter implements WebFilter {

    @Autowired
    private LocalRouteDefinitionRouteLocator localRouteDefinitionRouteLocator;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {


        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();
        if (path.equals("/route/dynamic/test")) {
            return chain.filter(exchange);
        }
        CallbackConfigEntity callbackConfig = LocalRouteDefinitionRouteLocator.CALLBACK_CONFIGS.get(path);
        if (callbackConfig == null) {
            return Mono.error(new IllegalArgumentException("非法请求"));

        }
        ServerHttpRequest newRequest = null;
        Triple<String, String, String> urlInfo;
        if (Boolean.TRUE.equals(callbackConfig.getOutPathFixed())) {
            try {
                urlInfo = URIUtils.getURLInfo(callbackConfig.getOutUrl());
            } catch (MalformedURLException e) {
                return Mono.error(new IllegalArgumentException("非法请求"));
            }
            newRequest = request.mutate().path(urlInfo.getMiddle()).build();
        } else {
            String outUrl = null;
            if ("query".equals(callbackConfig.getOutUrlInfo().getSource())) {
                outUrl = request.getQueryParams().getFirst(callbackConfig.getOutUrlInfo().getParameter());
            } else if ("header".equals(callbackConfig.getOutUrlInfo().getSource())) {
                outUrl = request.getHeaders().getFirst(callbackConfig.getOutUrlInfo().getParameter());
            }
            if (StringUtils.isNoneEmpty(callbackConfig.getOutUrlInfo().getPath())) {
                outUrl = Optional.ofNullable(outUrl).orElse("") + callbackConfig.getOutUrlInfo().getPath();
            }
            try {
                urlInfo = URIUtils.getURLInfo(outUrl);
                newRequest = request.mutate().path(urlInfo.getMiddle()).build();
                localRouteDefinitionRouteLocator.buildRouteDefinitions(callbackConfig, outUrl);
            } catch (Exception e) {
                System.err.println("error callback url=" + request.getPath().value());
                e.printStackTrace();
                return Mono.error(new IllegalArgumentException("回调地址错误"));
            }
        }

        return chain.filter(exchange.mutate().request(newRequest).build())
                .onErrorResume(throwable -> {
                    System.out.println("[Web]" + exchange.getRequest().getURI().getPath() + " 出错了!");
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