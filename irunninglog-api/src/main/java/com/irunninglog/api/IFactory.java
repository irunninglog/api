package com.irunninglog.api;

public interface IFactory {

    <T> T get(Class<T> clazz);

}
