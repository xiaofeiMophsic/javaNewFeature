package com.mophsic.proxy;

/**
 * @author xiaofei
 * @date 2017/2/14
 */
public class RealSubject implements Subject{
    @Override
    public String doSomething() {
        System.out.println("do something");
        return "real subject";
    }
}
