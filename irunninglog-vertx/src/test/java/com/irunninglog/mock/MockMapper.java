package com.irunninglog.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;


public class MockMapper implements IMapper {

    private final ObjectMapper mapper = new ObjectMapper();
    private final IFactory factory;

    public MockMapper(IFactory factory) {
        super();

        this.factory = factory;
    }

    @Override
    public String encode(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            throw new IllegalArgumentException("Unable to encode " + object);
        }
    }

    @Override
    public <T> T decode(final String string, final Class<T> clazz) {
        try {
            @SuppressWarnings("unchecked")
            Class<T> classToRead = clazz.isInterface() ? (Class<T>) factory.get(clazz).getClass() : clazz;
            return mapper.readValue(string, classToRead);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Can't decode " + string + " " + clazz, ex);
        }
    }

}
