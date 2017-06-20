package com.irunninglog.spring.security;

import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IAuthenticationService;
import com.irunninglog.api.security.IUser;
import com.irunninglog.strava.IStravaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
final class AuthenticationService implements IAuthenticationService {

    private final IStravaService stravaService;
//    private final int id;
//    private final String secret;

    @Autowired
    public AuthenticationService(IStravaService stravaService) throws UnsupportedEncodingException {
        this.stravaService = stravaService;
    }

    @Override
    public IUser authenticateToken(String token) throws AuthnException {
        return stravaService.userFromToken(token);
//        if (token == null) {
//            throw new AuthnException("No token provided");
//        } else if (!token.startsWith("Bearer ")) {
//            throw new AuthnException("Invalid token format");
//        } else {
//            try {
//                return verify(token);
//            } catch (Exception ex) {
//                LOG.error("Token verification failed", ex);
//                throw new AuthnException("Token verification failed");
//            }
//        }
    }

    @Override
    public IUser authenticateCode(String code) throws AuthnException {
        return stravaService.userFromCode(code);
    }

}
