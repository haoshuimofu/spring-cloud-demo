package com.demo.spring.cloud.route;

import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RouteUtils {

    /**
     * [GatewayProperties@4bdf routes = list[RouteDefinition{id='service_consumer', predicates=[PredicateDefinition{name='Path', args={_genkey_0=/consumer/auth/**}}], filters=[FilterDefinition{name='AuthFilterFactory', args={}}, FilterDefinition{name='Retry', args={retries=3, statuses=BAD_REQUEST, series=SERVER_ERROR, backoff.firstBackoff=1000ms, backoff.maxBackoff=2000ms, backoff.multiplier=1.5}}], uri=lb://service-consumer, order=0, metadata={}}, RouteDefinition{id='service_consumer_auth', predicates=[PredicateDefinition{name='Path', args={_genkey_0=/consumer/pass/**}}], filters=[], uri=lb://service-consumer, order=0, metadata={}}], defaultFilters = list[[empty]], streamingMediaTypes = list[text/event-stream, application/stream json], failOnRouteDefinitionError = true]
     * <p>
     * FilterDefinition{name='Retry', args={retries=3, statuses=BAD_REQUEST, series=SERVER_ERROR, backoff.firstBackoff=1000ms, backoff.maxBackoff=2000ms, backoff.multiplier=1.5}}]
     *
     * @param obj
     * @return
     */
    public static RouteDefinition buildRouteDefinition(Object obj) {
        RouteDefinition rd = new RouteDefinition();
        rd.setId("baidu");
        rd.setUri(URI.create("http://www.222baidu111.com"));

        PredicateDefinition pathPredicate = new PredicateDefinition();
        pathPredicate.setName("Path");
        Map<String, String> pathArgs = new HashMap<String, String>();
        pathArgs.put("pattern", "/s");
        pathPredicate.setArgs(pathArgs);

        rd.setPredicates(Collections.singletonList(pathPredicate));

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("url", "http://www.baidu.com/s");
        // timeout
//        metadata.put("connect-timeout", 1000);
//        metadata.put("response-timeout", 1000);
        rd.setMetadata(metadata);

        FilterDefinition retryFd = new FilterDefinition();
        retryFd.setName("Retry");
        Map<String, String> retryArgs = new HashMap<>();
        retryArgs.put("retries", "2");
        retryArgs.put("series", HttpStatus.Series.SERVER_ERROR.name());
        retryArgs.put("methods", HttpMethod.GET.name() + "," + HttpMethod.POST.name());
        // backoff
        retryArgs.put("backoff.firstBackoff", "3000ms");
        retryArgs.put("backoff.maxBackoff", "3000ms");
        retryArgs.put("backoff.multiplier", "2");
        retryFd.setArgs(retryArgs);

        rd.setFilters(Collections.singletonList(retryFd));

        return rd;

    }

}
