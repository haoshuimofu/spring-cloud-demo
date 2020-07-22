package com.demo.spring.cloud;

import com.demo.spring.cloud.client.OtherApiClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author wude
 * @date 2020/4/29 18:11
 */
@Component
public class OtherApiClientFallbackFactory implements FallbackFactory<OtherApiClient> {

    @Override
    public OtherApiClient create(Throwable throwable) {
        System.err.println(throwable.getMessage());
        return new OtherApiClient() {
            @Override
            public JsonResult doSomething() {
                return JsonResult.fail(-1, "别试了，啥也做不了啦！");
            }
        };
    }

}