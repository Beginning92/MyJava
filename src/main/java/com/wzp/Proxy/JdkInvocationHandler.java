package com.wzp.Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author: Administrator
 * @version: 1.0.0
 * @createTime: 2018/3/13
 * jdk动态代理是由java内部的反射机制来实现的
 */
public class JdkInvocationHandler implements InvocationHandler {
    private Object target; //们既然要做代理，我们必须知道我们是给谁做代理，这里的obj就是被代理者。

    JdkInvocationHandler() {
        super();
    }

    JdkInvocationHandler(Object target) {
        super();
        this.target = target;
    }

    //然后是invoke的三个参数、第一个参数就是代理者，如果你想对代理者做一些操作可以使用这个参数；第二个就是被执行的方法，第三个是执行该方法所需的参数。
    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {  //把我们想要通过代理者给被代理者追加的操作都写在invoke方法里面
        System.out.println(method.getName() + "++++++before " + method.getName() + "++++++");
        Object result = method.invoke(target, args);
        System.out.println(method.getName() + "++++++after " + method.getName() + "++++++");
        return result;

    }
}
