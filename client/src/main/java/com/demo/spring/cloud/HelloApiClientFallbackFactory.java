package com.demo.spring.cloud;

import com.demo.spring.cloud.client.HelloApiClient;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author wude
 * @date 2020/4/29 18:11
 */
@Component
public class HelloApiClientFallbackFactory implements FallbackFactory<HelloApiClient> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloApiClientFallbackFactory.class);

    @Override
    public HelloApiClient create(Throwable throwable) {
        LOGGER.error("## feign接口调用异常", throwable);
        return new HelloApiClient() {
            @Override
            public ResponseEntity<String> sayHello(String name) {
                String message = "hi, " + name + ", 接口熔断了!";
                return ResponseEntity.success(message);
            }
        };
    }

}