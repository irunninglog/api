package com.irunninglog.spring.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IAuthenticationService;
import com.irunninglog.api.security.IUser;
import com.irunninglog.spring.service.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.UnsupportedEncodingException;
import java.util.stream.Collectors;

@ApiService
final class AuthenticationService implements IAuthenticationService {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationService.class);

    private final IUserEntityRepository userEntityRepository;
    private final IFactory factory;
    private final JWTVerifier verifier;

    @Autowired
    public AuthenticationService(IUserEntityRepository userEntityRepository,
                                 IFactory factory,
                                 Environment environment) throws UnsupportedEncodingException {

        this.userEntityRepository = userEntityRepository;
        this.factory = factory;

        verifier = verifier(environment);
    }

    private JWTVerifier verifier(Environment environment) throws UnsupportedEncodingException {
        String authConfig = environment.getRequiredProperty("jwt");

        String[] tokens = authConfig.split("\\|");
        Algorithm algorithm = Algorithm.HMAC256(tokens[1]);
        return JWT.require(algorithm)
                .withIssuer(tokens[0])
                .build();
    }

    @Override
    public IUser authenticate(String token) throws AuthnException {
        if (token == null) {
            throw new AuthnException("No token provided");
        } else if (!token.startsWith("Bearer ")) {
            throw new AuthnException("Invalid token format");
        } else {
            try {
                return verify(token.split(" ")[1]);
            } catch (AuthnException ex) {
                throw ex;
            } catch (Exception ex) {
                LOG.error("Token verification failed", ex);
                throw new AuthnException("Token verification failed");
            }
        }
    }

    private IUser verify(String token) throws AuthnException {
        DecodedJWT decodedJWT = verifier.verify(token);

        String username = decodedJWT.getSubject();

        UserEntity userEntity = userEntityRepository.findByUsername(username);
        if (userEntity == null) {
            LOG.error("checkBasic:bad user:{}", username);
            throw new AuthnException("User not found");
        }

        return factory.get(IUser.class)
                .setId(userEntity.getId())
                .setUsername(userEntity.getUsername())
                .setAuthorities(userEntity.getAuthorities().stream().map(AuthorityEntity::getName).collect(Collectors.toList()));
    }

}
