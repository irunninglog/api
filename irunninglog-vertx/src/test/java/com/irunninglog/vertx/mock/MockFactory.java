package com.irunninglog.vertx.mock;

import com.irunninglog.api.dashboard.IDashboardInfo;
import com.irunninglog.api.dashboard.IGetDashboardRequest;
import com.irunninglog.api.dashboard.IGetDashboardResponse;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.profile.IGetProfileRequest;
import com.irunninglog.api.profile.IGetProfileResponse;
import com.irunninglog.api.security.IAuthnRequest;
import com.irunninglog.api.security.IAuthnResponse;
import com.irunninglog.api.workout.IGetWorkoutsRequest;
import com.irunninglog.api.workout.IGetWorkoutsResponse;

public class MockFactory implements IFactory {

    @Override
    public <T> T get(Class<T> clazz) {
        if (clazz == IAuthnRequest.class) {
            //noinspection unchecked
            return (T) new MockAuthnRequest();
        } else if (clazz == IAuthnResponse.class) {
            //noinspection unchecked
            return (T) new MockAuthnResponse();
        } else if (clazz == IGetProfileRequest.class) {
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
        } else {
            throw new IllegalArgumentException("Can't create class " + clazz);
        }
    }

}
