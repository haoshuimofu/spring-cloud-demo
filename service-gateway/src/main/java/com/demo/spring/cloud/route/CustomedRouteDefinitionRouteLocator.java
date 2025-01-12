package com.demo.spring.cloud.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CustomedRouteDefinitionRouteLocator implements RouteLocator, ApplicationEventPublisherAware {

    @Autowired
    private CustomedRouteDefinitionRepository customedRouteDefinitionRepository;
    private ApplicationEventPublisher publisher;

    /**
     * @return {@link org.springframework.cloud.gateway.route.CachingRouteLocator }
     */
    @Override
    public Flux<Route> getRoutes() {
        // 如果继续把customedRouteDefinitionRepository.route转Route, 将会出现重复Route, 可Debug
        // return customedRouteDefinitionRepository.getRouteDefinitions().map(RouteDefinitionConverter::toRoute);
        return Flux.empty();
    }

    public void saveRoute(Object obj) {
        RouteDefinition rd = RouteDefinitionConverter.buildRouteDefinition(obj);
        customedRouteDefinitionRepository.save(Mono.just(rd)).subscribe();
        publisher.publishEvent(new RefreshRoutesEvent(new Object()));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

}
