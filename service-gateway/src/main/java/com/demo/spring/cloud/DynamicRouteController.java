package com.demo.spring.cloud;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/route")
public class DynamicRouteController {


    @RequestMapping("/fixed/test")
    public String fixedRoute() {
        return "fixed url";
    }

    @RequestMapping("/dynamic/test")
    public String dynamicRoute(@RequestParam("url") String url) {
        return "dynamic url";
    }


}
