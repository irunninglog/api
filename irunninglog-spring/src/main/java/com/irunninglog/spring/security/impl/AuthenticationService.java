package com.irunninglog.spring.security.impl;

import com.irunninglog.security.*;
import com.irunninglog.service.Endpoint;
import com.irunninglog.spring.service.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.AntPathMatcher;

import java.text.MessageFormat;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@ApiService
public final class AuthenticationService implements IAuthenticationService {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationService.class);
    private static final String PATTERN = "/profiles/{0}/**";

    private final IUserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;
    private final AntPathMatcher matcher = new AntPathMatcher();

    @Autowired
    public AuthenticationService(IUserEntityRepository userEntityRepository, PasswordEncoder passwordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User authenticate(AuthnRequest request) throws AuthnException, AuthzException {
        LOG.info("authenticate:{}", request);

        if (request.getEndpoint().getControl() == AccessControl.DenyAll) {
            LOG.info("authenticate:denyAll:{}", request);
            throw new AuthzException("Not authorized for endpoint " + request.getEndpoint());
        } else if (request.getEndpoint().getControl() == AccessControl.AllowAnonymous) {
            LOG.info("authenticate:allowAll:{}", request);
            return null;
        }

        Credential credential = credential(request.getToken());

        LOG.info("authenticate:{}:{}", request, credential.username);

        User user = authenticate(credential, userEntityRepository.findByUsername(credential.username));

        authorize(user, request.getEndpoint(), request.getPath());

        return user;
    }

    private Credential credential(String token) throws AuthnException {
        try {
            String decoded = new String(Base64.getDecoder().decode(token.split(" ")[1]));
            String [] tokens = decoded.split(":");
            Credential credential = new Credential();
            credential.username = tokens[0];
            credential.password = tokens[1];

            return credential;
        } catch (Exception ex) {
            LOG.error("credential", ex);
            throw new AuthnException("Unable to decode credential");
        }
    }

    private User authenticate(Credential credential, UserEntity userEntity) throws AuthnException {
        if (userEntity == null) {
            LOG.error("authenticate:notFound:{}", credential.username);
            throw new AuthnException("User not found: " + credential.username);
        } else if (!passwordEncoder.matches(credential.password, userEntity.getPassword())) {
            LOG.error("authenticate:wrongPassword", credential.username);
            throw new AuthnException("Passwords don't match!");
        } else {
            List<String> authorities = userEntity.getAuthorities().stream().map(AuthorityEntity::getName).collect(Collectors.toList());

            String[] array = authorities.toArray(new String[authorities.size()]);
            return new User(userEntity.getId(), userEntity.getUsername(), array);
        }
    }

    private void authorize(User user, Endpoint endpoint, String path) throws AuthzException {
        if (endpoint.getControl() == AccessControl.AllowAll) {
            LOG.info("authorize:AllowAll:{}:{}:{}", user.getUsername(), endpoint, path);
        } else if (endpoint.getControl() == AccessControl.AllowProfile && isUserAllowed(user, path)) {
            LOG.info("authorize:AllowProfile:{}:{}:{}", user.getUsername(), endpoint, path);
        } else if (hasRole(user, "ADMIN")) {
            LOG.info("authorize:admin:{}:{}:{}", user.getUsername(), endpoint, path);
        } else {
            throw new AuthzException("User " + user.getUsername() + " cannot access " + path);
        }
    }

    private boolean isUserAllowed(User user, String path) {
        return hasRole(user, "MYPROFILE") &&
                matcher.match(MessageFormat.format(PATTERN, user.getId()), path);
    }

    private boolean hasRole(User user, String role) {
        for (String string : user.getAuthorities()) {
            if (string.equals(role)) {
                return true;
            }
        }

        return false;
    }

    private class Credential {
        private String username;
        private String password;
    }

}
