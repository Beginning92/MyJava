package com.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @RestController这个注解等价于spring mvc用法中的@Controller+@ResponseBody
 */
@PropertySource(value = {"classpath:conf/application.properties"},encoding="utf-8")
@RestController
public class HelloController {

    @Value("${who}")
    private String who;

    @RequestMapping("/hello")
    public String hello() {
        return "hello " + who;
    }
}
