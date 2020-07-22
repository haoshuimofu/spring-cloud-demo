package com.demo.spring.cloud.custom;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wude
 * @date 2020/5/16 18:01
 */
@Configuration
public class RoutesConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(r -> r.path("/service-provider-other/**")
                        .filters(filter -> filter.stripPrefix(1)
                                .filter(new MyGatewayFilter()) //增加自定义filter
                                .addRequestHeader("X-Response-Default-Foo", "Default-Bar"))
                        .uri("lb://SERVICE-PROVIDER-OTHER")
                        .order(0)
                        .id("ribbon-route")
                ).build();
    }
}