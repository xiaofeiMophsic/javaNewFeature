package com.mophsic.generics;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaofei
 * @date 2017/3/14
 */
public class ClassTypeCapture<T> {

    private Class<T> kind;
    private Map<String, Class<?>> classMap = new HashMap<>();

    public ClassTypeCapture(Class<T> kind) {
        this.kind = kind;
    }

    public boolean instanceOfKind(Object obj){
        return kind.isInstance(obj);
    }


    public <U> void addType(String typeName, Class<U> type){
        classMap.put(typeName, type);
    }

    public Object createNew(String typeName){
        Class<?> c = classMap.get(typeName);
        Object o = null;
        try {
            o = c.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            System.out.println("创建类实例失败");
        }
        return o;
    }

    public static void main(String[] args) {
        ClassTypeCapture<String> classTypeCapture = new ClassTypeCapture<>(String.class);

        classTypeCapture.addType("object", Object.class);

        Object o = classTypeCapture.createNew("object");
        System.out.println(o.getClass().getSimpleName());
    }
}
