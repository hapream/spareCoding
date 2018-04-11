package com.hapream;

public interface ProxyCreator {

    boolean canProxy(Class<?>[] proxyClasses);

    <T> T createInvokerProxy(ClassLoader loader, ObjectInvoker invoker, Class<?>... proxyClasses);
}
