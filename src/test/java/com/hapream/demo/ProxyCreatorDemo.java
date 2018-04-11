package com.hapream.demo;

import com.hapream.*;
import com.hapream.impl.CglibCreator;
import com.hapream.util.ProxyUtil;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;

public class ProxyCreatorDemo {

    @Test
    public void testCglib() {
        ProxyCreator proxyCreator = new CglibCreator();

        ObjectInvoker invoker = new ObjectInvoker() {
            public Object invoke(Object proxy, Method method, Object... arguments) throws Throwable {
                return "zmq";
            }
        };

        EchoService echoService = proxyCreator.createInvokerProxy(Thread.currentThread().getContextClassLoader(), invoker, EchoService.class);

        assertThat(echoService.echo("zyg"), Matchers.is("zmq"));

    }


    @Test
    public void testSpiProxy() {
        ProxyCreator proxyCreator = ProxyUtil.getInstance();
        ObjectInvoker objectInvoker = new ObjectInvoker() {
            @Override
            public Object invoke(Object proxy, Method method, Object... arguments) throws Throwable {
                return "hello";
            }
        };
        EchoService echoService = proxyCreator.createInvokerProxy(Thread.currentThread().getContextClassLoader(), objectInvoker, EchoService.class);
        assertThat(echoService.echo("wprld"), Matchers.is("hello"));
    }

    @Test
    public void testInterceptor() {
        ProxyCreator proxyCreator = ProxyUtil.getInstance();
        final Interceptor interceptor = new Interceptor() {
            @Override
            public Object intercepet(Invocation invocation) throws Throwable {
                return invocation.proceed() + "world";
            }
        };
        EchoService echoService = proxyCreator.createInterceptorProxy(Thread.currentThread().getContextClassLoader(),
                new EchoServiceImpl(), interceptor, EchoService.class);
        assertThat(echoService.echo("Hello "), Matchers.is("Hello world"));
    }

    @Test
    public void testDelegate() {
        ProxyCreator proxyCreator = ProxyUtil.getInstance();

        ObjectProvider provider = new ObjectProvider() {
            @Override
            public Object getObject() {
                return new DecoratorEchoService(new EchoServiceImpl());
            }
        };

        EchoService echoService = proxyCreator.createDelegatorProxy(Thread.currentThread().getContextClassLoader(), provider, EchoService.class);

        assertThat(echoService.echo("wow"), Matchers.is("WOW"));
    }

    private static class DecoratorEchoService implements EchoService {
        private EchoService echoService;


        public DecoratorEchoService(EchoService echoService) {
            this.echoService = echoService;
        }

        @Override
        public String echo(String message) {
            message = message.toUpperCase();
            return echoService.echo(message);
        }
    }
}
