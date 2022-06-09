package com.weiwei.easyplugin;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.PropertyPermission;

/**
 * @author weiwei
 * Created on 2022/2/9 5:21 下午
 */
public class Test {

    interface Bird {
        void fly();
        String eat(String food);
    }
    static class XiQue implements Bird{

        @Override
        public void fly() {
            System.out.println("XiQue flying...");
        }

        @Override
        public String eat(String food) {
            System.out.println("喜鹊在吃饭..."+food);
            return "喜鹊吃饭,吃了"+food;
        }

    }

    public static void main(String[] args) {
        Bird bird = new XiQue();
        MyInvocationHandler invocationHandler = new MyInvocationHandler(bird);
//        Bird proxyBird = (Bird) Proxy.newProxyInstance(bird.getClass().getClassLoader(), new Class[]{bird.getClass()},factory);
        Bird proxyBird = (Bird) Proxy.newProxyInstance(bird.getClass().getClassLoader(), bird.getClass().getInterfaces(),invocationHandler);
        proxyBird.fly();
        proxyBird.eat("汉堡");
    }

    static class MyInvocationHandler implements InvocationHandler {

        private Object mObject;

        public MyInvocationHandler(Object o){
            mObject = o;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//            System.out.println(method.getName()+"------");
            Object result = method.invoke(mObject, args);
            if ("eat".equals(method.getName())){
                System.out.println("代理方法插入eat()成功...");
            }
            System.out.println(result);
            return result;//result
        }
    }

}

