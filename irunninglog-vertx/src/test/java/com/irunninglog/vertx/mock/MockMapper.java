package com.irunninglog.vertx.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.irunninglog.api.dashboard.IDashboardInfo;
import com.irunninglog.api.dashboard.IGetDashboardRequest;
import com.irunninglog.api.dashboard.IGetDashboardResponse;
import com.irunninglog.api.data.*;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.ping.IPingRequest;
import com.irunninglog.api.ping.IPingResponse;
import com.irunninglog.api.profile.IGetProfileRequest;
import com.irunninglog.api.profile.IGetProfileResponse;
import com.irunninglog.api.report.IGetDataSetResponse;
import com.irunninglog.api.report.IGetMultiSetResponse;
import com.irunninglog.api.report.IGetReportRequest;
import com.irunninglog.api.workout.*;
import com.irunninglog.vertx.Envelope;

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
            if (clazz == IGetProfileRequest.class) {
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
            } else if (clazz == IPingRequest.class) {
                //noinspection unchecked
                return (T) objectMapper.readValue(string, MockPingRequest.class);
            } else if (clazz == IPingResponse.class) {
                //noinspection unchecked
                return (T) objectMapper.readValue(string, MockPingResponse.class);
            } else if (clazz == IGetRoutesResponse.class) {
                //noinspection unchecked
                return (T) objectMapper.readValue(string, MockGetRoutesResponse.class);
            } else if (clazz == IGetRunsResponse.class) {
                //noinspection unchecked
                return (T) objectMapper.readValue(string, MockGetRunsResponse.class);
            } else if (clazz == IGetShoesResponse.class) {
                //noinspection unchecked
                return (T) objectMapper.readValue(string, MockGetShoesResponse.class);
            } else if (clazz == IGetDataRequest.class) {
                //noinspection unchecked
                return (T) objectMapper.readValue(string, MockGetDataRequest.class);
            } else if (clazz == IRoutes.class) {
                //noinspection unchecked
                return (T) objectMapper.readValue(string, MockRoutes.class);
            } else if (clazz == IGetDataSetResponse.class) {
                //noinspection unchecked
                return (T) objectMapper.readValue(string, MockGetDataSetResponse.class);
            } else if (clazz == IGetReportRequest.class) {
                //noinspection unchecked
                return (T) objectMapper.readValue(string, MockGetReportRequest.class);
            } else if (clazz == IGetMultiSetResponse.class) {
                //noinspection unchecked
                return (T) objectMapper.readValue(string, MockGetMultiSetResponse.class);
            } else if (clazz == IWorkoutEntry.class) {
                //noinspection unchecked
                return (T) objectMapper.readValue(string, MockWorkoutEntry.class);
            } else if (clazz == IPutWorkoutRequest.class) {
                //noinspection unchecked
                return (T) objectMapper.readValue(string, MockPutWorkoutRequest.class);
            } else if (clazz == IPutWorkoutResponse.class) {
                //noinspection unchecked
                return (T) objectMapper.readValue(string, MockPutWorkoutResponse.class);
            } else if (clazz == IDeleteWorkoutRequest.class) {
                //noinspection unchecked
                return (T) objectMapper.readValue(string, MockDeleteWorkoutRequest.class);
            } else if (clazz == IDeleteWorkoutResponse.class) {
                //noinspection unchecked
                return (T) objectMapper.readValue(string, MockDeleteWorkoutResponse.class);
            } else if (clazz == Envelope.class) {
                //noinspection unchecked
                return (T) objectMapper.readValue(string, Envelope.class);
            } else {
                throw new IllegalArgumentException("Can't read class " + clazz);
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

}
