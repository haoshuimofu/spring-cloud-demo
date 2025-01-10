package com.demo.spring.cloud;

import com.demo.spring.cloud.route.CustomedRouteDefinitionRepository;
import com.demo.spring.cloud.route.CustomedRouteLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/route")
public class DynamicRouteController {

    @Autowired
    private CustomedRouteDefinitionRepository customedRouteDefinitionRepository;

    @Autowired
    private CustomedRouteLocator customedRouteLocator;

    @RequestMapping("/save")
    public String save() {
        customedRouteLocator.saveRoute(new Object());
        return "SUCCESS";
    }

    @RequestMapping("/exception")
    public String exception() {
        throw new RuntimeException("exception");
    }


}
