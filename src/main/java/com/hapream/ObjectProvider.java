package com.hapream;

import java.io.Serializable;

public interface ObjectProvider<T> extends Serializable {

    T getObject();

}
