package com.demo.spring.cloud.custom;

import com.alibaba.fastjson2.JSON;
import com.demo.spring.cloud.ResponseEntity;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class AuthFilterFactory extends AbstractGatewayFilterFactory<Object> {

    @Override
    public GatewayFilter apply(Object config) {
        GatewayFilter filter = new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                ServerHttpRequest request = exchange.getRequest();
                ServerHttpResponse response = exchange.getResponse();

                String uri = request.getURI().getPath();
                if (uri.startsWith("/consumer/auth") && !request.getQueryParams().containsKey("sign")) {
                    // 无权限，返回401 Unauthorized
//                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return response.writeWith(Mono.fromSupplier(
                            () -> response.bufferFactory().wrap(JSON.toJSONBytes(ResponseEntity.fail("100", "no permission")))));
                }
                // 如果有权限，继续请求链
                return chain.filter(exchange);
            }
        };

        // 设置 Filter 优先级在写入响应前
        int order = NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 1;
        return new OrderedGatewayFilter(filter, order);
    }

}
