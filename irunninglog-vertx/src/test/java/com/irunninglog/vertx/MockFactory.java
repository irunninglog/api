package com.irunninglog.vertx;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.profile.IGetProfileRequest;
import com.irunninglog.api.profile.IGetProfileResponse;
import com.irunninglog.api.security.IAuthnRequest;
import com.irunninglog.api.security.IAuthnResponse;
import org.mockito.Mockito;

public class MockFactory implements IFactory {

    @Override
    public <T> T get(Class<T> clazz) {
        if (clazz == IAuthnRequest.class) {
            //noinspection unchecked
            return (T) new MockAuthnRequest();
        } else if (clazz == IAuthnResponse.class) {
            //noinspection unchecked
            return (T) new MockGetProfileResponse();
        } else if (clazz == IGetProfileRequest.class) {
            //noinspection unchecked
            return (T) new MockGetProfileRequest();
        } else if (clazz == IGetProfileResponse.class) {
            //noinspection unchecked
            return (T) new MockGetProfileResponse();
        } else {
            return Mockito.mock(clazz);
        }
    }

}
