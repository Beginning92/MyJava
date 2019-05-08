package com;

import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Hello world!
 *
 */
//@ComponentScan(
//        basePackages = {"com.fenqile", "com.dshamc"},
//        useDefaultFilters = false, includeFilters = {
//        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Component.class),
//        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Service.class)
//})
//@PropertySources({
//        @PropertySource("classpath:dubbo_common_config.properties"),
//        @PropertySource("classpath:fql_dubbo_common_config.properties"),
//        @PropertySource("classpath:job.properties"),
//        @PropertySource("classpath:app.properties")
//})
//@Configuration
//@EnableAspectJAutoProxy
//@ImportResource("classpath*:/META-INF/spring/*.xml")
@SpringBootApplication
public class App
{
    public static void main( String[] args )
    {
        /*System.out.println( "Hello World!" );*/
        SpringApplication.run(App.class, args);// 记得运行main方法
    }

    @Bean
    public HystrixCommandAspect hystrixCommandAspect() {
        return new HystrixCommandAspect();
    }

}