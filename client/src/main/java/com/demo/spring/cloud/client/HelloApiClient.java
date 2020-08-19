package com.demo.spring.cloud.client;

import com.demo.spring.cloud.FeignClientConfiguration;
import com.demo.spring.cloud.HelloApiClientFallbackFactory;
import com.demo.spring.cloud.api.HelloApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author wude
 * @date 2020/4/29 11:11
 */
@FeignClient(value = "service-provider",
        configuration = FeignClientConfiguration.class,
        fallbackFactory = HelloApiClientFallbackFactory.class)
public interface HelloApiClient extends HelloApi {
}