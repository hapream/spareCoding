package com.hapream;

import java.util.Arrays;
import java.util.ServiceLoader;

public class DefaultProxyCreator implements ProxyCreator {

    public static final DefaultProxyCreator INSTANCE = new DefaultProxyCreator();

    private static final ServiceLoader<ProxyCreator> SERVICES = ServiceLoader.load(ProxyCreator.class);

    public boolean canProxy(Class<?>[] proxyClasses) {
        return false;
    }

    public <T> T createInvokerProxy(ClassLoader loader, ObjectInvoker invoker, Class<?>... proxyClasses) {
        T result = (T) getCapableProxyCreator(proxyClasses).createInvokerProxy(loader, invoker, proxyClasses);
        return result;
    }


    private ProxyCreator getCapableProxyCreator(Class<?>... proxyClasses) {
        for (ProxyCreator proxyCreator : SERVICES) {
            if (proxyCreator.canProxy(proxyClasses)) {
                return proxyCreator;
            }
        }
        throw new IllegalArgumentException("Could not proxy " + Arrays.toString(proxyClasses));
    }
}
