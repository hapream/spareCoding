package com.hapream;

public interface ProxyCreator {

    <T> T createInvokerProxy(ClassLoader loader, ObjectInvoker invoker, Class<?>... proxyClass);
}
