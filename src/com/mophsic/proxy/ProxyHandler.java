package com.mophsic.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author xiaofei
 * @date 2017/2/14
 */
public class ProxyHandler implements InvocationHandler {

    private Object proxy;

    ProxyHandler(Object proxy){
        this.proxy = proxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("doSomething".equals(method.getName())){
            System.out.println("before");
            Object object = method.invoke(this.proxy, args);
            System.out.println("after");
            return object;
        }
        return null;
    }
}
