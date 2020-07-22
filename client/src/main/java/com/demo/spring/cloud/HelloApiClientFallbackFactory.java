package com.demo.spring.cloud;

import com.demo.spring.cloud.client.HelloApiClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author wude
 * @date 2020/4/29 18:11
 */
@Component
public class HelloApiClientFallbackFactory implements FallbackFactory<HelloApiClient> {

    @Override
    public HelloApiClient create(Throwable throwable) {
        System.err.println(throwable.getMessage());
        return new HelloApiClient() {
            @Override
            public JsonResult<String> sayHello(String name) {
                return JsonResult.success("别试了，Hystrix熔断了!");
            }
        };
    }

}