package com.hapream;

public interface ProxyCreator {

    boolean canProxy(Class<?>[] proxyClasses);

    <T> T createInvokerProxy(ClassLoader loader, ObjectInvoker invoker, Class<?>... proxyClasses);


    <T> T createInterceptorProxy(ClassLoader loader, Object target, Interceptor interceptor, Class<?>... proxyClasses);

    <T> T createDelegatorProxy(ClassLoader loader, ObjectProvider objectProvider, Class<?>... proxyClasses);
}
