package com.hapream.impl;

import com.google.common.collect.Sets;
import com.hapream.ObjectInvoker;
import com.hapream.ProxyCreator;
import com.hapream.util.ProxyUtil;
import net.sf.cglib.proxy.*;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;

public class CglibCreator implements ProxyCreator {

    private static final CglibProxyFactoryCallbackFilter callbackFilter = new CglibProxyFactoryCallbackFilter();

    public <T> T createInvokerProxy(ClassLoader loader, ObjectInvoker invoker, Class<?>... proxyClasses) {
        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(loader);
        enhancer.setInterfaces(toInterfaces(proxyClasses));
        enhancer.setSuperclass(getSupperclass(proxyClasses));
        enhancer.setCallbackFilter(callbackFilter);
        enhancer.setCallbacks(new Callback[]{
                new InvokerBridge(invoker), new EqualsHandler(), new HashCodeHandler()
        });
        T result = (T) enhancer.create();
        return result;
    }


    /**
     * @param proxyClasses
     * @return
     */
    private static Class<?>[] toInterfaces(Class<?>... proxyClasses) {
        Set<Class<?>> interfaces = Sets.newLinkedHashSet();
        for (Class<?> proxyClass : proxyClasses) {
            if (proxyClass.isInterface()) {
                interfaces.add(proxyClass);
            }
        }
        interfaces.add(Serializable.class);
        return interfaces.toArray(new Class<?>[interfaces.size()]);
    }


    private static Class<?>[] toNonInterFaces(Class<?>[] proxyClasses) {
        Set<Class<?>> superclasses = Sets.newLinkedHashSet();
        for (Class<?> proxyClass : proxyClasses) {
            if (!proxyClass.isInterface()) {
                superclasses.add(proxyClass);
            }
        }
        return superclasses.toArray(new Class[superclasses.size()]);
    }

    /**
     * //TODO need fixed
     *
     * @param proxyClasses
     * @return
     */
    private static Class<?> getSupperclass(Class<?>[] proxyClasses) {
        final Class<?>[] superclasses = toNonInterFaces(proxyClasses);
        switch (superclasses.length) {
            case 0:
                return Object.class;
            case 1:
                Class<?> superclass = superclasses[0];
                if (Modifier.isFinal(superclass.getModifiers())) {
                    throw new RuntimeException("");
                }

        }
        return null;
    }

    private static class CglibProxyFactoryCallbackFilter implements CallbackFilter {

        public int accept(Method method) {
            if (ProxyUtil.isEqualsMethod(method)) {
                return 1;
            } else if (ProxyUtil.isHashCode(method)) {
                return 2;
            }
            return 0;
        }
    }

    private static class EqualsHandler implements MethodInterceptor, Serializable {

        private static final long serialVersionUID = -6077833602011809674L;

        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

            return Boolean.valueOf(obj == args[0]);
        }
    }

    private static class HashCodeHandler implements MethodInterceptor, Serializable {

        private static final long serialVersionUID = -2918448227794094973L;

        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

            return Integer.valueOf(System.identityHashCode(obj));
        }
    }

    private static class InvokerBridge implements InvocationHandler, Serializable {

        private ObjectInvoker original;

        public InvokerBridge(ObjectInvoker invoker) {
            this.original = invoker;
        }


        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return original.invoke(proxy, method, args);
        }
    }

}
