package com.hapream;

import java.io.Serializable;

public interface Interceptor extends Serializable {

    Object intercepet(Invocation invocation) throws Throwable;
}
