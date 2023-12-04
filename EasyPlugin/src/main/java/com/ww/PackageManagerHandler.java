package com.ww;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 使用动态代理 代理packageManager 解决插件启动appcompatActivity 异常问题
 *
 * @author weiwei
 * Created on 2022/2/9 4:12 下午
 */
@Deprecated
public class PackageManagerHandler implements InvocationHandler {
    private Object obj;

    public PackageManagerHandler(Object obj) {
        this.obj = obj;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //在原方法前边动态插入代码
        Object result = method.invoke(obj, args);
        //在原方法后边动态插入代码

        return result;
    }
}
