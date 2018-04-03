package com.wzp.Proxy;

import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author: Administrator
 * @version: 1.0.0
 * @createTime: 2018/3/13
 */
public class Main {
    public static void main(String[] args) {
//        jdkTest();
        cglibTest();
    }

    public static void jdkTest() {
        BizService bizService = new BizServiceImpl();
        //创建一个InvocationHandler，描述我们希望代理者执行哪些操作
        InvocationHandler invocationHandler = new JdkInvocationHandler(bizService);
        //通过刚才创建的InvocationHandler，创建真正的代理者。第一个参数是类加载器，第二个参数是这个代理者实现哪些接口(与被代理者实现的是相同的接口)
        BizService bizServiceProxy = (BizService) Proxy.newProxyInstance(bizService.getClass().getClassLoader(),
                bizService.getClass().getInterfaces(), invocationHandler);
        bizServiceProxy.doBiz();
    }

    public static void cglibTest(){
        CglibProxy cglibProxy = new CglibProxy();

        Enhancer enhancer = new Enhancer();  //主要的增强类
        enhancer.setSuperclass(BizServiceImpl.class);  //设置父类，被增强的类
        enhancer.setCallback(cglibProxy);  //回调对象

        BizService o = (BizService) enhancer.create();//用cglibProxy来增强UserServiceImpl
        o.doBiz();
    }
}
