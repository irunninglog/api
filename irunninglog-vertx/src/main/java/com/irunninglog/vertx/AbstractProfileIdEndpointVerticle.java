package com.irunninglog.vertx;

import com.irunninglog.api.IProfileIdRequest;
import com.irunninglog.api.IResponse;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.IUser;

public abstract class AbstractProfileIdEndpointVerticle<Q extends IProfileIdRequest, S extends IResponse> extends AbstractRequestResponseVerticle<Q, S> {

    protected AbstractProfileIdEndpointVerticle(IFactory factory, IMapper mapper) {
        super(factory, mapper);
    }

    protected final boolean admin(IUser user) {
        return user.hasAuthority("ADMIN");
    }

    protected final boolean matches(IUser user, Q request) {
        return user != null && (admin(user) || user.getId() == request.getProfileId());
    }

}
