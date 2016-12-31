package com.irunninglog.vertx.endpoint.authn;

import com.irunninglog.security.*;
import com.irunninglog.service.ResponseStatus;
import com.irunninglog.vertx.Address;
import com.irunninglog.vertx.endpoint.AbstractRequestResponseVerticle;
import com.irunninglog.vertx.endpoint.EndpointVerticle;

@EndpointVerticle(constructorArgs = {
        IAuthenticationService.class,
        IAuthorizationService.class
})
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
            status = ResponseStatus.Unauthenticated;
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
