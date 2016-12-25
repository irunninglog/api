package com.irunninglog.spring.security.impl;

import com.irunninglog.security.AuthnException;
import com.irunninglog.security.IAuthenticationService;
import com.irunninglog.security.User;
import com.irunninglog.spring.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

@ApiService
public final class AuthenticationService implements IAuthenticationService {

    private final IUserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(IUserEntityRepository userEntityRepository, PasswordEncoder passwordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User authenticate(String username, String password) throws AuthnException {
        UserEntity userEntity = userEntityRepository.findByUsername(username);

        if (userEntity == null) {
            throw new AuthnException("User not found: " + username);
        } else if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw new AuthnException("Passwords don't match!");
        } else {
            List<String> authorities = userEntity.getAuthorities().stream().map(AuthorityEntity::getName).collect(Collectors.toList());

            String[] array = authorities.toArray(new String[authorities.size()]);
            return new User(userEntity.getId(), userEntity.getUsername(), array);
        }
    }

}
