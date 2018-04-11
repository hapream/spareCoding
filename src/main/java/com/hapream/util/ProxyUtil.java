package com.hapream.util;

import com.hapream.DefaultProxyCreator;
import com.hapream.ProxyCreator;

import java.lang.reflect.Method;

public class ProxyUtil {


    public static ProxyCreator getInstance() {
        return DefaultProxyCreator.INSTANCE;
    }

    public static boolean isHashCode(Method method) {
        return "hashCode".equals(method.getName()) && Integer.TYPE.equals(method.getReturnType()) && method.getParameterTypes().length == 0;
    }

    public static boolean isEqualsMethod(Method method) {

        return "equals".equals(method.getName()) && Boolean.TYPE.equals(method.getReturnType()) &&
                method.getParameterTypes().length == 1 && Object.class == method.getParameterTypes()[0];
    }
}
