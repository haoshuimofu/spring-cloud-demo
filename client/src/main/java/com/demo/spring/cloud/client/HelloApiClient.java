package com.demo.spring.cloud.client;

import com.demo.spring.cloud.FeignClientConfiguration;
import com.demo.spring.cloud.HelloApiClientFallbackFactory;
import com.demo.spring.cloud.api.HelloApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * <p>
 *     触发熔断情况：
 *     服务不存在-确实不存在，也可能客户端缓存的servers还没刷新
 *     接口超时
 *     下游异常- 如果下游使用@ControllerAdvice包装了内部异常，不会触发熔断，整个feign调用是成功的
 * </p>
 * <p>一旦开启熔断，业务调用的地方会返回默认值，不会有异常抛出</p>
 * @author wude
 * @date 2020/4/29 11:11
 */
@FeignClient(
        value = "service-provider",
        fallbackFactory = HelloApiClientFallbackFactory.class, // 服务不存在、接口超时、下游异常 都会触发熔断
        configuration = FeignClientConfiguration.class
)
public interface HelloApiClient extends HelloApi {
}