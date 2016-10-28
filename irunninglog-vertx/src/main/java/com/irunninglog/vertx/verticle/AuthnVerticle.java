package com.irunninglog.vertx.verticle;

import com.irunninglog.api.security.*;
import com.irunninglog.api.service.ResponseStatus;
import com.irunninglog.vertx.Address;

public class AuthnVerticle extends AbstractRequestResponseVerticle<AuthnRequest, AuthnResponse> {

    private final IAuthenticationService authenticationService;

    public AuthnVerticle(IAuthenticationService authenticationService) {
        super(AuthnRequest.class, AuthnResponse::new);

        this.authenticationService = authenticationService;
    }

    @Override
    protected AuthnResponse handle(AuthnRequest request) {
        ResponseStatus status = ResponseStatus.Ok;
        User user = null;

        try {
            user = authenticationService.authenticate(request.getUsername(), request.getPassword());

            logger.info("handle:user:{}", user);
        } catch (AuthnException ex) {
            logger.error("Unable to authenticate", ex);
            status = ResponseStatus.Unauthnticated;
        }

        return new AuthnResponse().setStatus(status).setBody(user);
    }

    @Override
    protected Address address() {
        return Address.Authenticate;
    }

}
