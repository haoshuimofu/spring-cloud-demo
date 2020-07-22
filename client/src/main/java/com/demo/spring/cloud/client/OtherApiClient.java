package com.demo.spring.cloud.client;

import com.demo.spring.cloud.FeignClientOtherConfiguration;
import com.demo.spring.cloud.OtherApiClientFallbackFactory;
import com.demo.spring.cloud.api.OtherApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author wude
 * @date 2020/4/29 15:42
 */
@FeignClient(value = "service-provider-other", path = "service-provider",
        configuration = FeignClientOtherConfiguration.class,
        fallbackFactory = OtherApiClientFallbackFactory.class)
// 如果feign.hystrix.enabled=true开启熔断，而某个FeignClient没有配置fallback则会NullException
public interface OtherApiClient extends OtherApi {
}