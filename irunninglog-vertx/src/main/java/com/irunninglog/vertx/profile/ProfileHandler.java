package com.irunninglog.vertx.profile;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.profile.IGetProfileRequest;
import com.irunninglog.api.profile.IGetProfileResponse;
import com.irunninglog.vertx.AbstractRouteHandler;
import com.irunninglog.vertx.RouteHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

@RouteHandler(endpoint = Endpoint.PROFILE, request = IGetProfileRequest.class, response = IGetProfileResponse.class)
public class ProfileHandler extends AbstractRouteHandler<IGetProfileRequest,IGetProfileResponse> {

    public ProfileHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper);
    }

    @Override
    protected void request(IGetProfileRequest request, RoutingContext routingContext) {
        super.request(request, routingContext);

        request.setToken(routingContext.request().getHeader("Authorization"));
    }

}
