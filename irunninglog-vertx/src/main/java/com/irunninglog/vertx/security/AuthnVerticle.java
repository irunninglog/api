package com.irunninglog.vertx.security;

import com.irunninglog.api.IFactory;
import com.irunninglog.api.security.IAuthnRequest;
import com.irunninglog.api.security.IAuthnResponse;
import com.irunninglog.api.security.AuthzException;
import com.irunninglog.api.security.*;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.vertx.endpoint.AbstractRequestResponseVerticle;

public final class AuthnVerticle extends AbstractRequestResponseVerticle<IAuthnRequest, IAuthnResponse> {

    public static final String ADDRESS = "18680d22-5eff-4dd3-ad31-5eed34198143";

    private final IAuthenticationService authenticationService;

    public AuthnVerticle(IAuthenticationService authenticationService, IFactory factory) {
        super(factory, IAuthnRequest.class, IAuthnResponse.class);

        this.authenticationService = authenticationService;
    }

    @Override
    protected void handle(IAuthnRequest request, IAuthnResponse response) {
        ResponseStatus status = ResponseStatus.Ok;
        IUser user = null;

        try {
            user = authenticationService.authenticate(request.getEndpoint(), request.getPath(), request.getToken());

            logger.info("handle:user:{}", user);
        } catch (AuthzException ex) {
            logger.error("Unable to authorize", ex);
            status = ResponseStatus.Unauthorized;
        } catch (Exception ex) {
            logger.error("Unable to authenticate", ex);
            status = ResponseStatus.Unauthenticated;
        }

        response.setStatus(status).setBody(user);
    }

    @Override
    protected String address() {
        return ADDRESS;
    }

}
