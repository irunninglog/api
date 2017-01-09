package com.irunninglog.vertx.security;

import com.irunninglog.security.*;
import com.irunninglog.service.ResponseStatus;
import com.irunninglog.vertx.endpoint.AbstractRequestResponseVerticle;

public final class AuthnVerticle extends AbstractRequestResponseVerticle<AuthnRequest, AuthnResponse> {

    public static final String ADDRESS = "18680d22-5eff-4dd3-ad31-5eed34198143";

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
            user = authenticationService.authenticate(request);

            logger.info("handle:user:{}", user);
        } catch (AuthzException ex) {
            logger.error("Unable to authorize", ex);
            status = ResponseStatus.Unauthorized;
        } catch (Exception ex) {
            logger.error("Unable to authenticate", ex);
            status = ResponseStatus.Unauthenticated;
        }

        return new AuthnResponse().setStatus(status).setBody(user);
    }

    @Override
    protected String address() {
        return ADDRESS;
    }

}
