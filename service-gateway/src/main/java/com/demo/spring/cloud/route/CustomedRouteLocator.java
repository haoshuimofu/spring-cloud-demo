package com.demo.spring.cloud.route;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.RetryGatewayFilterFactory;
import org.springframework.cloud.gateway.handler.predicate.PathRoutePredicateFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

@Component
public class CustomedRouteLocator implements RouteLocator, InitializingBean, ApplicationEventPublisherAware {


    @Autowired
    private ConfigurableApplicationContext context;

    @Autowired
    private CustomedRouteDefinitionRepository customedRouteDefinitionRepository;
    private ApplicationEventPublisher publisher;

    private RouteLocatorBuilder routeLocatorBuilder;

    @Override

    public Flux<Route> getRoutes() {
        return routeLocatorBuilder.routes().build().getRoutes();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.routeLocatorBuilder = new RouteLocatorBuilder(context);
    }

    public void save(Object obj) {

        Function<PredicateSpec, Route.AsyncBuilder> fn = r ->
                r.path("/s").uri("https://www.baiud.com");

        PathRoutePredicateFactory.Config pathConfig = new PathRoutePredicateFactory.Config();
        ;
        pathConfig.setPatterns(Collections.singletonList("/s"));

        Predicate<ServerWebExchange> pathPredicate = new PathRoutePredicateFactory().apply(pathConfig);

        RetryGatewayFilterFactory.RetryConfig retryConfig = new RetryGatewayFilterFactory.RetryConfig();
        retryConfig.setRetries(3);
        retryConfig.setStatuses(HttpStatus.INTERNAL_SERVER_ERROR);
        retryConfig.setMethods(HttpMethod.GET, HttpMethod.POST);

        GatewayFilter retryFilter = new RetryGatewayFilterFactory().apply(retryConfig);

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("url", "https://www.baiud.com/s");


        Route route = Route.builder()
                .id("baidu")
                .and(pathPredicate)
                .filter(retryFilter)
                .metadata(metadata)
                .build();

        routeLocatorBuilder.routes().route("baidu", fn).build();
    }

    public void saveRoute(Object obj) {
        RouteDefinition rd = RouteUtils.buildRouteDefinition(obj);
        customedRouteDefinitionRepository.save(Mono.just(rd)).subscribe();
        publisher.publishEvent(new RefreshRoutesEvent(new Object()));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
