package com.hapream.demo;

import com.hapream.ObjectInvoker;
import com.hapream.ProxyCreator;
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
}
