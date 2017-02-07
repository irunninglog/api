package com.irunninglog.vertx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.profile.IGetProfileRequest;
import com.irunninglog.api.profile.IGetProfileResponse;
import com.irunninglog.api.security.IAuthnRequest;
import com.irunninglog.api.security.IAuthnResponse;

import java.io.IOException;

public class MockMapper implements IMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String encode(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Override
    public <T> T decode(String string, Class<T> clazz) {
        try {
            if (clazz == IAuthnRequest.class) {
                //noinspection unchecked
                return (T) objectMapper.readValue(string, MockAuthnRequest.class);
            } else if (clazz == IAuthnResponse.class) {
                //noinspection unchecked
                return (T) objectMapper.readValue(string, MockAuthnResponse.class);
            } else if (clazz == IGetProfileRequest.class) {
                //noinspection unchecked
                return (T) objectMapper.readValue(string, MockGetProfileRequest.class);
            } else if (clazz == IGetProfileResponse.class) {
                //noinspection unchecked
                return (T) objectMapper.readValue(string, MockGetProfileResponse.class);
            }

            return objectMapper.readValue(string, clazz);
        } catch (IOException e) {
            return null;
        }
    }

}
