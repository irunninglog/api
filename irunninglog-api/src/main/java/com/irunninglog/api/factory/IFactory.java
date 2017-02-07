package com.irunninglog.api.factory;

public interface IFactory {

    <T> T get(Class<T> clazz);

}
