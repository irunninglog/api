package com.irunninglog.vertx.security;

import com.irunninglog.security.*;
import com.irunninglog.service.ResponseStatus;
import com.irunninglog.vertx.endpoint.AbstractRequestResponseVerticle;

public final class AuthnVerticle extends AbstractRequestResponseVerticle<AuthnRequest, AuthnResponse> {

    public static final String ADDRESS = "18680d22-5eff-4dd3-ad31-5eed34198143";

    private final IAuthenticationService authenticationService;
    private final IAuthorizationService authorizationService;

    public AuthnVerticle(IAuthenticationService authenticationService, IAuthorizationService authorizationService) {
        super(AuthnRequest.class, AuthnResponse::new);

        this.authenticationService = authenticationService;
        this.authorizationService = authorizationService;
    }

    @Override
    protected AuthnResponse handle(AuthnRequest request) {
        ResponseStatus status = ResponseStatus.Ok;
        User user = null;

        try {
            user = authorizationService
                    .authorize(authenticationService.authenticate(request.getUsername(),
                            request.getPassword()), request.getPath());

            logger.info("handle:user:{}", user);
        } catch (AuthnException ex) {
            logger.error("Unable to authenticate", ex);
            status = ResponseStatus.Unauthenticated;
        } catch (AuthzException ex) {
            logger.error("Unable to authorize", ex);
            status = ResponseStatus.Unauthorized;
        }

        return new AuthnResponse().setStatus(status).setBody(user);
    }

    @Override
    protected String address() {
        return ADDRESS;
    }

}
