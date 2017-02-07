package com.irunninglog.api.mapping;

public interface IMapper {

    String encode(Object object);

    <T> T decode(String string, Class<T> clazz);
}
