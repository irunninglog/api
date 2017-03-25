package com.irunninglog.spring.security;

import com.irunninglog.api.AccessControl;
import com.irunninglog.api.Endpoint;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.AuthzException;
import com.irunninglog.api.security.IAuthenticationService;
import com.irunninglog.api.security.IUser;
import com.irunninglog.spring.context.AuthenticationServiceConfig;
import com.irunninglog.spring.service.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.AntPathMatcher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@ApiService
final class AuthenticationService implements IAuthenticationService {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationService.class);
    private static final String PATTERN = "/profiles/{0}/**";

    private final IUserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;
    private final IFactory factory;
    private final AntPathMatcher matcher = new AntPathMatcher();

    private final long duration;
    private final String key;

    @Autowired
    public AuthenticationService(IUserEntityRepository userEntityRepository,
                                 PasswordEncoder passwordEncoder,
                                 IFactory factory,
                                 Environment environment) {

        this.userEntityRepository = userEntityRepository;
        this.passwordEncoder = passwordEncoder;
        this.factory = factory;

        AuthenticationServiceConfig config = new AuthenticationServiceConfig(environment.getRequiredProperty("authenticationService"));
        duration = config.getDuration();
        key = config.getKey();
    }

    @Override
    public IUser authenticate(Endpoint endpoint, String path, String token) throws AuthnException, AuthzException {
        LOG.info("authenticate:{}:{}", endpoint, path);

        if (endpoint.getControl() == AccessControl.DENY) {
            LOG.info("authenticate:denyAll:{}", endpoint);
            throw new AuthzException("Not authorized for endpoint " + endpoint);
        } else if (endpoint.getControl() == AccessControl.ANONYMOUS) {
            LOG.info("authenticate:allowAll:{}", endpoint);
            return null;
        }

        IUser user;
        if (token != null && token.startsWith("Token ")) {
            user = tokenAuth(token);
        } else if (token != null) {
            user = basicAuth(token);
        } else {
            throw new AuthnException("No token provided");
        }

        LOG.info("authenticate:{}:{}", endpoint, user);

        authorize(user, endpoint, path);

        return user;
    }

    private IUser basicAuth(String token) throws AuthnException {
        String username;
        String password;

        try {
            String decoded = new String(Base64.getDecoder().decode(token.split(" ")[1]));
            String [] tokens = decoded.split(":");
            username = tokens[0];
            password = tokens[1];
        } catch (Exception ex) {
            LOG.error("credential", ex);
            throw new AuthnException("Unable to decode credential");
        }

        return checkBasic(username, password);
    }

    private IUser checkBasic(String username, String password) throws AuthnException {
        UserEntity userEntity = userEntityRepository.findByUsername(username);
        if (userEntity == null) {
            LOG.error("checkBasic:bad user:{}", username);
            throw new AuthnException("User not found");
        }

        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            LOG.error("checkBasic:password mismatch");
            throw new AuthnException("Passwords don't match");
        }

        return factory.get(IUser.class)
                .setId(userEntity.getId())
                .setUsername(userEntity.getUsername())
                .setAuthorities(userEntity.getAuthorities().stream().map(AuthorityEntity::getName).collect(Collectors.toList()));
    }

    private IUser tokenAuth(String token) throws AuthnException {
        long expiry;
        String username;
        String signature;

        try {
            String decoded = new String(Base64.getDecoder().decode(token.split(" ")[1]));
            String [] tokens = decoded.split(":");
            expiry = Long.parseLong(tokens[0]);
            username = tokens[1];
            signature = tokens[2];
        } catch (Exception ex) {
            LOG.error("tokenAuth", ex);
            throw new AuthnException("Unable to decode token credential");
        }

        LOG.info("tokenAuth:{}:{}", username, expiry);

        return checkToken(expiry, username, signature);
    }

    private IUser checkToken(long expiry, String username, String signature) throws AuthnException {
        long now = System.currentTimeMillis();
        if (expiry < now) {
            LOG.error("checkToken:expired:{}:{}", new Date(now), new Date(expiry));
            throw new AuthnException("Token has expired");
        }

        UserEntity userEntity = userEntityRepository.findByUsername(username);
        if (userEntity == null) {
            LOG.error("checkToken:bad user:{}", username);
            throw new AuthnException("User not found");
        }

        String generatedSignature = signature(userEntity.getUsername(), userEntity.getPassword(), Long.toString(expiry));
        if (!generatedSignature.equals(signature)) {
            LOG.error("checkToken:signature mismatch");
            throw new AuthnException("Signatures don't match");
        }

        return factory.get(IUser.class)
                .setId(userEntity.getId())
                .setUsername(userEntity.getUsername())
                .setAuthorities(userEntity.getAuthorities().stream().map(AuthorityEntity::getName).collect(Collectors.toList()));
    }

    private void authorize(IUser user, Endpoint endpoint, String path) throws AuthzException {
        if (endpoint.getControl() == AccessControl.ALLOW) {
            LOG.info("authorize:ALLOW:{}:{}:{}", user.getUsername(), endpoint, path);
        } else if (endpoint.getControl() == AccessControl.PROFILE && isUserAllowed(user, path)) {
            LOG.info("authorize:PROFILE:{}:{}:{}", user.getUsername(), endpoint, path);
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

    @Override
    public String token(IUser user) throws AuthnException {
        UserEntity userEntity = userEntityRepository.findOne(user.getId());

        long expireTime = System.currentTimeMillis() + 1000L * duration;
        String tokenExpiryTime = Long.toString(expireTime);

        String signature = signature(userEntity.getUsername(), userEntity.getPassword(), tokenExpiryTime);
        String unencoded = tokenExpiryTime + ":" + user.getUsername() + ":" + signature;
        return Base64.getEncoder().encodeToString(unencoded.getBytes());
    }

    private String signature(String username, String password, String expiry) throws AuthnException {
        String data = username + ":" + expiry + ":" + password + ":" + key;

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            LOG.error("No such algorithm", ex);
            throw new AuthnException("No MD5 algorithm available!");
        }

        return new String(Hex.encode(digest.digest(data.getBytes())));
    }

}
