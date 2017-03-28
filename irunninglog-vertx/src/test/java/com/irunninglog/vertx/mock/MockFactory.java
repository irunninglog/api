package com.irunninglog.vertx.mock;

import com.irunninglog.api.dashboard.IDashboardInfo;
import com.irunninglog.api.dashboard.IGetDashboardRequest;
import com.irunninglog.api.dashboard.IGetDashboardResponse;
import com.irunninglog.api.data.IGetDataRequest;
import com.irunninglog.api.data.IGetRoutesResponse;
import com.irunninglog.api.data.IGetRunsResponse;
import com.irunninglog.api.data.IGetShoesResponse;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.ping.IPingRequest;
import com.irunninglog.api.ping.IPingResponse;
import com.irunninglog.api.profile.IGetProfileRequest;
import com.irunninglog.api.profile.IGetProfileResponse;
import com.irunninglog.api.report.IGetDataSetResponse;
import com.irunninglog.api.report.IGetMultiSetResponse;
import com.irunninglog.api.security.IForbiddenRequest;
import com.irunninglog.api.security.ILoginRequest;
import com.irunninglog.api.security.ILoginResponse;
import com.irunninglog.api.workout.*;

public class MockFactory implements IFactory {

    @Override
    public <T> T get(Class<T> clazz) {
        if (clazz == IGetProfileRequest.class) {
            //noinspection unchecked
            return (T) new MockGetProfileRequest();
        } else if (clazz == IGetProfileResponse.class) {
            //noinspection unchecked
            return (T) new MockGetProfileResponse();
        } else if (clazz == IGetDashboardRequest.class) {
            //noinspection unchecked
            return (T) new MockGetDashboardRequest();
        } else if (clazz == IGetDashboardResponse.class) {
            //noinspection unchecked
            return (T) new MockGetDashboardResponse();
        } else if (clazz == IDashboardInfo.class) {
            //noinspection unchecked
            return (T) new MockDashboardInfo();
        } else if (clazz == IGetWorkoutsRequest.class) {
            //noinspection unchecked
            return (T) new MockGetWorkoutsRequest();
        } else if (clazz == IGetWorkoutsResponse.class) {
            //noinspection unchecked
            return (T) new MockGetWorkoutsResponse();
        } else if (clazz == ILoginRequest.class) {
            //noinspection unchecked
            return (T) new MockLoginRequest();
        } else if (clazz == ILoginResponse.class) {
            //noinspection unchecked
            return (T) new MockLoginResponse();
        } else if (clazz == IForbiddenRequest.class) {
            //noinspection unchecked
            return (T) new MockForbiddenRequest();
        } else if (clazz == IPingRequest.class) {
            //noinspection unchecked
            return (T) new MockPingRequest();
        } else if (clazz == IPingResponse.class) {
            //noinspection unchecked
            return (T) new MockPingResponse();
        } else if (clazz == IGetDataRequest.class) {
            //noinspection unchecked
            return (T) new MockGetDataRequest();
        } else if (clazz == IGetRoutesResponse.class) {
            //noinspection unchecked
            return (T) new MockGetRoutesResponse();
        } else if (clazz == IGetRunsResponse.class) {
            //noinspection unchecked
            return (T) new MockGetRunsResponse();
        } else if (clazz == IGetShoesResponse.class) {
            //noinspection unchecked
            return (T) new MockGetShoesResponse();
        } else if (clazz == IGetDataSetResponse.class) {
            //noinspection unchecked
            return (T) new MockGetDataSetResponse();
        } else if (clazz == IGetMultiSetResponse.class) {
            //noinspection unchecked
            return (T) new MockGetMultiSetResponse();
        } else if (clazz == IPutWorkoutRequest.class) {
            //noinspection unchecked
            return (T) new MockPutWorkoutRequest();
        } else if (clazz == IPutWorkoutResponse.class) {
            //noinspection unchecked
            return (T) new MockPutWorkoutResponse();
        } else if (clazz == IDeleteWorkoutRequest.class) {
            //noinspection unchecked
            return (T) new MockDeleteWorkoutRequest();
        } else if (clazz == IDeleteWorkoutResponse.class) {
            //noinspection unchecked
            return (T) new MockDeleteWorkoutResponse();
        }   else {
            throw new IllegalArgumentException("Can't create class " + clazz);
        }
    }

}
