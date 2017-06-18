package com.irunninglog.spring.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
final class Mapper implements IMapper {

    private static final Logger LOG = LoggerFactory.getLogger(Mapper.class);

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
            LOG.error("encode:illegal:" + object, ex);
            throw new IllegalArgumentException("Can't encode " + object, ex);
        }
    }

    @Override
    public <T> T decode(final String string, final Class<T> clazz) {
        try {
            @SuppressWarnings("unchecked")
            Class<T> classToRead = clazz.isInterface() ? (Class<T>) factory.get(clazz).getClass() : clazz;
            return objectMapper.readValue(string, classToRead);
        } catch (Exception ex) {
            LOG.error("decode:illegal:" + string + ":" + clazz, ex);
            throw new IllegalArgumentException("Can't decode " + string + " " + clazz, ex);
        }
    }

}
