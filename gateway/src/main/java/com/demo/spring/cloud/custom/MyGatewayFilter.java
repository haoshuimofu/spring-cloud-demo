package com.demo.spring.cloud.custom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author wude
 * @date 2020/5/16 17:58
 */
public class MyGatewayFilter implements GatewayFilter, Ordered {


    private static final Logger log = LoggerFactory.getLogger(MyGatewayFilter.class);

    private static final String TIME = "Time";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(TIME, System.currentTimeMillis());
        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    Long start = exchange.getAttribute(TIME);
                    if (start != null) {
                        log.info("exchange request uri:" + exchange.getRequest().getURI() + ", Time:" + (System.currentTimeMillis() - start) + "ms");
                    }
                })
        );
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

}