package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
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

    @Autowired
    Environment environment;

    @RequestMapping("/hello")
    public String hello() {
        System.out.println(environment.getProperty("who"));
        return "hello " + who;
    }
}
