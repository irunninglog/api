package com.irunninglog.spring.security;

import com.irunninglog.api.AccessControl;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.AuthzException;
import com.irunninglog.api.security.*;
import com.irunninglog.api.Endpoint;
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
final class AuthenticationService implements IAuthenticationService {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationService.class);
    private static final String PATTERN = "/profiles/{0}/**";

    private final IUserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;
    private final IFactory factory;
    private final AntPathMatcher matcher = new AntPathMatcher();

    @Autowired
    public AuthenticationService(IUserEntityRepository userEntityRepository,
                                 PasswordEncoder passwordEncoder,
                                 IFactory factory) {

        this.userEntityRepository = userEntityRepository;
        this.passwordEncoder = passwordEncoder;
        this.factory = factory;
    }

    @Override
    public IUser authenticate(Endpoint endpoint, String path, String token) throws AuthnException, AuthzException {
        LOG.info("authenticate:{}:{}", endpoint, path);

        if (endpoint.getControl() == AccessControl.DenyAll) {
            LOG.info("authenticate:denyAll:{}", endpoint);
            throw new AuthzException("Not authorized for endpoint " + endpoint);
        } else if (endpoint.getControl() == AccessControl.AllowAnonymous) {
            LOG.info("authenticate:allowAll:{}", endpoint);
            return null;
        }

        Credential credential = credential(token);

        LOG.info("authenticate:{}:{}", endpoint, credential.getUsername());

        IUser user = authenticate(credential, userEntityRepository.findByUsername(credential.getUsername()));

        authorize(user, endpoint, path);

        return user;
    }

    private Credential credential(String token) throws AuthnException {
        try {
            String decoded = new String(Base64.getDecoder().decode(token.split(" ")[1]));
            String [] tokens = decoded.split(":");
            return new Credential(tokens[0], tokens[1]);
        } catch (Exception ex) {
            LOG.error("credential", ex);
            throw new AuthnException("Unable to decode credential");
        }
    }

    private IUser authenticate(Credential credential, UserEntity userEntity) throws AuthnException {
        if (userEntity == null) {
            LOG.error("authenticate:notFound:{}", credential.getUsername());
            throw new AuthnException("User not found: " + credential.getUsername());
        } else if (!passwordEncoder.matches(credential.getPassword(), userEntity.getPassword())) {
            LOG.error("authenticate:wrongPassword", credential.getUsername());
            throw new AuthnException("Passwords don't match!");
        } else {
            List<String> authorities = userEntity.getAuthorities().stream().map(AuthorityEntity::getName).collect(Collectors.toList());

            return factory.get(IUser.class)
                    .setId(userEntity.getId())
                    .setUsername(userEntity.getUsername())
                    .setAuthorities(authorities);
        }
    }

    private void authorize(IUser user, Endpoint endpoint, String path) throws AuthzException {
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

    private boolean isUserAllowed(IUser user, String path) {
        return hasRole(user, "MYPROFILE") &&
                matcher.match(MessageFormat.format(PATTERN, String.valueOf(user.getId())), path);
    }

    private boolean hasRole(IUser user, String role) {
        for (String string : user.getAuthorities()) {
            if (string.equals(role)) {
                return true;
            }
        }

        return false;
    }

}
