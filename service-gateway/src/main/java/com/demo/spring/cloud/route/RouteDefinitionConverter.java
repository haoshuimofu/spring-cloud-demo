package com.demo.spring.cloud.route;

import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.RetryGatewayFilterFactory;
import org.springframework.cloud.gateway.handler.predicate.PathRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.time.Duration;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RouteDefinitionConverter {

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
        metadata.put("connect-timeout", 1000);
        metadata.put("response-timeout", 1000);
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

    /**
     * RouteDefinition{id='baidu', predicates=[PredicateDefinition{name='Path', args={pattern=/s}}], filters=[FilterDefinition{name='Retry', args={retries=2, backoff.maxBackoff=3000ms, series=SERVER_ERROR, methods=GET,POST, backoff.firstBackoff=3000ms, backoff.multiplier=2}}], uri=http://www.222baidu111.com, order=0, metadata={connect-timeout=1000, response-timeout=1000, url=http://www.baidu.com/s}}
     * RouteDefinition{id='baidu', predicates=[PredicateDefinition{name='Path', args={pattern=/s}}], filters=[FilterDefinition{name='Retry', args={retries=2, backoff.maxBackoff=3000ms, series=SERVER_ERROR, methods=GET,POST, backoff.firstBackoff=3000ms, backoff.multiplier=2}}], uri=http://www.222baidu111.com, order=0, metadata={connect-timeout=1000, response-timeout=1000, url=http://www.baidu.com/s}}"
     *
     * @param routeDefinition
     * @return
     */
    public static Route toRoute(RouteDefinition routeDefinition) {
        // Path
        PredicateDefinition pathPredicateDefinition = routeDefinition.getPredicates().stream()
                .filter(p -> "Path".equals(p.getName()))
                .findFirst().get();
        PathRoutePredicateFactory.Config pathConfig = new PathRoutePredicateFactory.Config();
        pathConfig.setPatterns(Arrays.asList(pathPredicateDefinition.getArgs().get("pattern").split(",")));
        Predicate<ServerWebExchange> pathPredicate = new PathRoutePredicateFactory().apply(pathConfig);

        // Retry
        FilterDefinition retryFilterDefinition = routeDefinition.getFilters().stream()
                .filter(f -> "Retry".equals(f.getName()))
                .findFirst().orElse(null);
        GatewayFilter retryFilter = null;
        if (retryFilterDefinition != null) {
            Map<String, String> retryArgs = retryFilterDefinition.getArgs();
            RetryGatewayFilterFactory.RetryConfig retryConfig = new RetryGatewayFilterFactory.RetryConfig();
            retryConfig.setRetries(Integer.parseInt(retryArgs.get("retries")));

            String[] series = retryArgs.getOrDefault("series", "").split(",");
            List<HttpStatus.Series> seriesList = Arrays.stream(series)
                    .map(HttpStatus.Series::valueOf)
                    .collect(Collectors.toList());
            if (!seriesList.isEmpty()) {
                retryConfig.setSeries(seriesList.toArray(new HttpStatus.Series[0]));
            }

            String[] methods = retryArgs.getOrDefault("methods", "").split(",");
            List<HttpMethod> methodList = Arrays.stream(methods)
                    .map(HttpMethod::resolve)
                    .collect(Collectors.toList());
            retryConfig.setMethods(methodList.toArray(new HttpMethod[0]));

            String firstBackoffMillis = retryArgs.getOrDefault("backoff.firstBackoff", "");
            if (!firstBackoffMillis.isEmpty()) {
                RetryGatewayFilterFactory.BackoffConfig backoffConfig = new RetryGatewayFilterFactory.BackoffConfig();
                backoffConfig.setFirstBackoff(Duration.ofMillis(Long.parseLong(firstBackoffMillis.substring(0, firstBackoffMillis.length() - 2))));
                backoffConfig.setMaxBackoff(backoffConfig.getFirstBackoff());
                backoffConfig.setFactor(1);
                backoffConfig.setBasedOnPreviousValue(true);
                retryConfig.setBackoff(backoffConfig);
            }
            retryFilter = new RetryGatewayFilterFactory().apply(retryConfig);

        }
        Route.AsyncBuilder routeBuilder = Route.async(routeDefinition).predicate(pathPredicate);
        Optional.ofNullable(retryFilter).ifPresent(routeBuilder::filter);
        return routeBuilder.build();
    }

}
