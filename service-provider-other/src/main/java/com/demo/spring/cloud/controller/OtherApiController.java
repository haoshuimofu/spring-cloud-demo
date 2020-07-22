package com.demo.spring.cloud.controller;

import com.demo.spring.cloud.JsonResult;
import com.demo.spring.cloud.api.OtherApi;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wude
 * @date 2020/4/29 13:37
 */
@RestController
public class OtherApiController implements OtherApi {

    @Override
    public JsonResult doSomething() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return JsonResult.success("Sorry, I don't know what I can do!");
    }
}