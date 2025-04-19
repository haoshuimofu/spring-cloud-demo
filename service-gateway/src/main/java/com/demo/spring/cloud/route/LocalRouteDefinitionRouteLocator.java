package com.demo.spring.cloud.route;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
public class LocalRouteDefinitionRouteLocator implements InitializingBean, ApplicationEventPublisherAware {

    public static Map<String, CallbackConfigEntity> CALLBACK_CONFIGS = new HashMap<>();

    @Autowired
    private LocalRouteDefinitionRepository localRouteDefinitionRepository;
    private ApplicationEventPublisher publisher;

    public void buildRouteDefinitions(CallbackConfigEntity callbackConfig, String url) throws Exception {
        localRouteDefinitionRepository.save(Mono.just(RouteDefinitionConverter.buildRouteDefinition(callbackConfig, url))).subscribe();
        publisher.publishEvent(new RefreshRoutesEvent(new Object()));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initRoutes();
    }

    private void initRoutes() throws Exception {
        CallbackConfigEntity callbackConfig = new CallbackConfigEntity();
        callbackConfig.setTitle("固定外调URL");
        callbackConfig.setInPath("/route/fixed");
        callbackConfig.setOutPathFixed(Boolean.TRUE);
        callbackConfig.setOutUrl("http://localhost:8080/route/fixed/test");
        callbackConfig.setTimeoutMillis(1000);
        callbackConfig.setRetryOnError(true);
        callbackConfig.setRetryTimes(2);
        callbackConfig.setRetryIntervalMillis(1000);
        localRouteDefinitionRepository.save(Mono.just(RouteDefinitionConverter.buildRouteDefinition(callbackConfig))).subscribe();

        CallbackConfigEntity callbackConfig1 = new CallbackConfigEntity();
        callbackConfig1.setTitle("动态外调URL");
        callbackConfig1.setInPath("/route/dynamic");
        callbackConfig1.setOutPathFixed(Boolean.FALSE);
        CallbackConfigEntity.OutUrlInfo outUrlInfo = new CallbackConfigEntity.OutUrlInfo();
        outUrlInfo.setSource("query");
        outUrlInfo.setParameter("url");
        outUrlInfo.setPath("/route/dynamic/test");
        callbackConfig1.setOutUrlInfo(outUrlInfo);
        callbackConfig1.setTimeoutMillis(1000);
        callbackConfig1.setRetryOnError(true);
        callbackConfig1.setRetryTimes(2);
        callbackConfig1.setRetryIntervalMillis(1000);
//        localRouteDefinitionRepository.save(Mono.just(RouteDefinitionConverter.buildRouteDefinition(callbackConfig1))).subscribe();
        CALLBACK_CONFIGS.put(callbackConfig.getInPath(), callbackConfig);
        CALLBACK_CONFIGS.put(callbackConfig1.getInPath(), callbackConfig1);
        publisher.publishEvent(new RefreshRoutesEvent(new Object()));
    }

}
