package com.hapream.demo;

import com.hapream.ObjectInvoker;
import com.hapream.ProxyCreator;
import com.hapream.impl.CglibCreator;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertThat;
import org.hamcrest.Matchers;

public class ProxyCreatorDemo {

    @Test
    public void testCglib(){
        ProxyCreator proxyCreator = new CglibCreator();

        ObjectInvoker invoker = new ObjectInvoker() {
            public Object invoke(Object proxy, Method method, Object... arguments) throws Throwable {
                return "zmq";
            }
        };

        EchoService echoService = proxyCreator.createInvokerProxy(Thread.currentThread().getContextClassLoader(), invoker, EchoService.class);

        assertThat(echoService.echo("zyg"), Matchers.is("zmq"));


    }
}
