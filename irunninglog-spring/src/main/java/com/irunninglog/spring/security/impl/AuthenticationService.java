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
        if (request.getEndpoint().getControl() == AccessControl.DenyAll) {
            throw new AuthzException("Nobody is allowed to access endpoint: " + request.getEndpoint());
        }

        User user = authenticate(request, userEntityRepository.findByUsername(request.getUsername()));

        authorize(user, request.getEndpoint(), request.getPath());

        return user;
    }

    private User authenticate(AuthnRequest request, UserEntity userEntity) throws AuthnException {
        if (userEntity == null) {
            throw new AuthnException("User not found: " + request.getUsername());
        } else if (!passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
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

}
