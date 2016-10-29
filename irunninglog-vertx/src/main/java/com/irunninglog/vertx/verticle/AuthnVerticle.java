package com.irunninglog.vertx.verticle;

import com.irunninglog.api.security.*;
import com.irunninglog.api.service.ResponseStatus;
import com.irunninglog.vertx.Address;

public class AuthnVerticle extends AbstractRequestResponseVerticle<AuthnRequest, AuthnResponse> {

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
            status = ResponseStatus.Unauthnticated;
        } catch (AuthzException ex) {
            logger.error("Unable to authorize", ex);
            status = ResponseStatus.Unauthorized;
        }

        return new AuthnResponse().setStatus(status).setBody(user);
    }

    @Override
    protected Address address() {
        return Address.Authenticate;
    }

}
