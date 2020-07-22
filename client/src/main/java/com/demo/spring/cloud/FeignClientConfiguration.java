package com.demo.spring.cloud;

import feign.Request;
import feign.RequestInterceptor;
import feign.Retryer;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

/**
 * @See https://cloud.spring.io/spring-cloud-static/spring-cloud-openfeign/2.2.2.RELEASE/reference/html/#spring-cloud-feign-overriding-defaults
 * 类上不需要打@Configuration注解，否则将成为默认配置，除非ComponentScan里排除
 *
 * @author wude
 * @date 2020/4/29 15:18
 */
public class FeignClientConfiguration {


    @Bean
    public Retryer retryer() {
        return Retryer.NEVER_RETRY;
    }

    /**
     * 连接时间和数据读取时间，超时：java.net.SocketTimeoutException: Read timed out
     *
     * @return
     */
    @Bean
    public Request.Options options() {
        return new Request.Options(5, TimeUnit.SECONDS, 3, TimeUnit.SECONDS, true);
    }

    @Bean
    public RequestInterceptor headerInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("TEST", "TEST");
        };
    }

}