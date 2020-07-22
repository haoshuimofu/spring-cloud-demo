package com.demo.spring.cloud;

import feign.Request;
import feign.RequestInterceptor;
import feign.Retryer;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

/**
 * @author wude
 * @date 2020/4/29 15:43
 */
public class FeignClientOtherConfiguration {

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
        return new Request.Options(5, TimeUnit.SECONDS, 10, TimeUnit.SECONDS, true);
    }

    @Bean
    public RequestInterceptor headerInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("TEST", "TEST");
        };
    }

}