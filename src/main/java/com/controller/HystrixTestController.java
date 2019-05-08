package com.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 熔断器使用
 *
 * https://github.com/Netflix/Hystrix/wiki/Configuration
 *
 * @author: beginningwang
 * @version: 1.0.0
 * @createTime: 2019/5/8
 */
@RestController
public class HystrixTestController {

    /**
     * 注解熔断器的方法一定要是public
     * @return
     */
    @HystrixCommand(
            groupKey="UserGroup", commandKey = "GetUserByIdCommand",
            fallbackMethod = "fallbackSearchAll",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1400")
            })
    @RequestMapping(value = "/test")
    public String getTest() {
        try {
            int i = 1/0;
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
//            e.printStackTrace();
        }
        return "test";
    }


    private String fallbackSearchAll() {
        return "error";
    }

}
