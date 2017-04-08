package com.irunninglog.spring.security;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IAuthenticationService;
import com.irunninglog.api.security.IUser;
import com.irunninglog.spring.service.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Base64;
import java.util.stream.Collectors;

@ApiService
final class AuthenticationService implements IAuthenticationService {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationService.class);

    private final IUserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;
    private final IFactory factory;

    @Autowired
    public AuthenticationService(IUserEntityRepository userEntityRepository,
                                 PasswordEncoder passwordEncoder,
                                 IFactory factory) {

        this.userEntityRepository = userEntityRepository;
        this.passwordEncoder = passwordEncoder;
        this.factory = factory;
    }

    @Override
    public IUser authenticate(String token) throws AuthnException {
        IUser user;
        if (token != null) {
            user = basicAuth(token);
        } else {
            throw new AuthnException("No token provided");
        }

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

}
