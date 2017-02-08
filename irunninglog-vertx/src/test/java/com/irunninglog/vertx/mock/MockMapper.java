package com.irunninglog.vertx.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.irunninglog.api.dashboard.IDashboardInfo;
import com.irunninglog.api.dashboard.IGetDashboardRequest;
import com.irunninglog.api.dashboard.IGetDashboardResponse;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.profile.IGetProfileRequest;
import com.irunninglog.api.profile.IGetProfileResponse;
import com.irunninglog.api.security.IAuthnRequest;
import com.irunninglog.api.security.IAuthnResponse;
import com.irunninglog.api.workout.IGetWorkoutsRequest;
import com.irunninglog.api.workout.IGetWorkoutsResponse;

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
            } else if (clazz == IGetDashboardRequest.class) {
                //noinspection unchecked
                return (T) objectMapper.readValue(string, MockGetDashboardRequest.class);
            } else if (clazz == IGetDashboardResponse.class) {
                //noinspection unchecked
                return (T) objectMapper.readValue(string, MockGetDashboardResponse.class);
            } else if (clazz == IGetDashboardResponse.class) {
                //noinspection unchecked
                return (T) objectMapper.readValue(string, MockGetDashboardResponse.class);
            } else if (clazz == IDashboardInfo.class) {
                //noinspection unchecked
                return (T) objectMapper.readValue(string, MockDashboardInfo.class);
            } else if (clazz == IGetWorkoutsRequest.class) {
                //noinspection unchecked
                return (T) objectMapper.readValue(string, MockGetWorkoutsRequest.class);
            } else if (clazz == IGetWorkoutsResponse.class) {
                //noinspection unchecked
                return (T) objectMapper.readValue(string, MockGetWorkoutsResponse.class);
            } else {
                throw new IllegalArgumentException("Can't read class " + clazz);
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

}
