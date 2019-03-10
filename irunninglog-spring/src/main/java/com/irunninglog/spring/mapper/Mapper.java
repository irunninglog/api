package com.irunninglog.spring.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
final class Mapper implements IMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final IFactory factory;

    @Autowired
    public Mapper(IFactory factory) {
        this.factory = factory;
    }

    @Override
    public String encode(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Can't encode " + object, ex);
        }
    }

    @Override
    public <T> T decode(final String string, final Class<T> clazz) {
        try {
            Class<T> classToRead = clazz.isInterface() ? (Class<T>) factory.get(clazz).getClass() : clazz;
            return objectMapper.readValue(string, classToRead);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Can't decode " + string + " " + clazz, ex);
        }
    }

}
