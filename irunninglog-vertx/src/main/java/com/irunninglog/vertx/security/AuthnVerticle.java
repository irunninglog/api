package com.irunninglog.vertx.security;

import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.*;
import com.irunninglog.vertx.endpoint.AbstractRequestResponseVerticle;

public final class AuthnVerticle extends AbstractRequestResponseVerticle<IAuthnRequest, IAuthnResponse> {

    public static final String ADDRESS = "18680d22-5eff-4dd3-ad31-5eed34198143";

    private final IAuthenticationService authenticationService;

    public AuthnVerticle(IAuthenticationService authenticationService, IFactory factory, IMapper mapper) {
        super(factory, mapper, IAuthnRequest.class, IAuthnResponse.class);

        this.authenticationService = authenticationService;
    }

    @Override
    protected void handle(IAuthnRequest request, IAuthnResponse response) {
        ResponseStatus status = ResponseStatus.OK;
        IUser user = null;
        String token = null;

        try {
            user = authenticationService.authenticate(request.getEndpoint(), request.getPath(), request.getToken());

            if (user != null) {
                token = authenticationService.token(user);
            }

            logger.info("handle:user:{}", user);
        } catch (AuthzException ex) {
            logger.error("Unable to authorize", ex);
            status = ResponseStatus.UNAUTHORIZED;
        } catch (Exception ex) {
            logger.error("Unable to authenticate", ex);
            status = ResponseStatus.UNAUTHENTICATED;
        }

        //noinspection unchecked
        response.setToken(token).setStatus(status).setBody(user);
    }

    @Override
    protected String address() {
        return ADDRESS;
    }

}
