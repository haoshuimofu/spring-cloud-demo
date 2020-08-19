package com.demo.spring.cloud.client;

import com.demo.spring.cloud.FeignClientConfiguration;
import com.demo.spring.cloud.api.HelloApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author wude
 * @date 2020/4/29 11:11
 */
@FeignClient(value = "service-provider",
        configuration = FeignClientConfiguration.class)
public interface HelloApiClient extends HelloApi {
}