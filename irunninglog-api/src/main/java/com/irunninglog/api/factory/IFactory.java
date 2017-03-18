package com.irunninglog.api.factory;

@FunctionalInterface
public interface IFactory {

    <T> T get(Class<T> clazz);

}
