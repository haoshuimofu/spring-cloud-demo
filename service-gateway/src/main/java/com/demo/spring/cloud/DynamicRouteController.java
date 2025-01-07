package com.demo.spring.cloud;

import com.demo.spring.cloud.route.DynamicRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/route")
public class DynamicRouteController {

    @Autowired
    private DynamicRouteService dynamicRouteService;

    @RequestMapping("/update")
    public String updateRoute() {

        RouteDefinition routeDefinition = new RouteDefinition();
        // 设置routeDefinition的属性，例如
        routeDefinition.setId("baidu");
        routeDefinition.setUri(URI.create("https://www.baidu.com"));

        PredicateDefinition predicate = new PredicateDefinition();
        predicate.setName("Path");
        // 设置断言需要的参数，例如Path的断言需要一个pattern
        Map<String, String> args = new HashMap<>();
        args.put("pattern", "/s/**");
        predicate.setArgs(args);

        routeDefinition.setPredicates(Arrays.asList(predicate));


        dynamicRouteService.updateRoute(routeDefinition);
        return "SUCCESS";
    }

}
