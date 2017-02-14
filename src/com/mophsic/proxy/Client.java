package com.mophsic.proxy;

import java.lang.reflect.Proxy;

/**
 * @author xiaofei
 * @date 2017/2/14
 */
public final class Client {
    public static void main(String[] args) {
        Subject subject = new RealSubject();

        Subject proxy = (Subject) Proxy
                .newProxyInstance(
                        Thread.currentThread().getContextClassLoader(),
                        new Class[]{Subject.class},
                        new ProxyHandler(subject));

        String str = proxy.doSomething();
        System.out.println(str);
    }
}
