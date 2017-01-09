package com.irunninglog.spring.security.impl;

import com.irunninglog.Gender;
import com.irunninglog.Unit;
import com.irunninglog.security.*;
import com.irunninglog.service.Endpoint;
import com.irunninglog.spring.AbstractTest;
import com.irunninglog.spring.profile.impl.IProfileEntityRepository;
import com.irunninglog.spring.profile.impl.ProfileEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.Assert.*;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
public class AuthenticationServiceTest extends AbstractTest {

    @Autowired
    private IAuthenticationService authenticationService;
    @Autowired
    private IProfileEntityRepository profileEntityRepository;
    @Autowired
    private IUserEntityRepository userEntityRepository;
    @Autowired
    private IAuthorityEntityRepository authorityEntityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void before() {
        ProfileEntity entity = new ProfileEntity();
        entity.setEmail("allan@irunninglog.com");
        entity.setPassword(passwordEncoder.encode("password"));
        entity.setFirstName("Allan");
        entity.setLastName("Lewis");
        entity.setBirthday(LocalDate.now());
        entity.setGender(Gender.Male);
        entity.setPreferredUnits(Unit.English);
        entity.setWeekStart(DayOfWeek.MONDAY);

        entity = profileEntityRepository.save(entity);

        AuthorityEntity authorityEntity = new AuthorityEntity();
        authorityEntity.setName("foo");
        authorityEntity = authorityEntityRepository.save(authorityEntity);

        UserEntity userEntity = userEntityRepository.findOne(entity.getId());
        userEntity.getAuthorities().add(authorityEntity);
        userEntityRepository.save(userEntity);
    }

    @After
    public void after() {
        userEntityRepository.deleteAll();
        authorityEntityRepository.deleteAll();
    }

    @Test
    public void success() throws AuthnException, AuthzException {
        User user = authenticationService.authenticate(new AuthnRequest()
                .setUsername("allan@irunninglog.com")
                .setPassword("password").setEndpoint(Endpoint.Ping));
        assertEquals("allan@irunninglog.com", user.getUsername());
        assertEquals(1, user.getAuthorities().size());
    }

    @Test
    public void notFound() throws AuthzException {
        try {
            authenticationService.authenticate(new AuthnRequest()
                    .setUsername("nobody@irunninglog.com")
                    .setPassword("password").setEndpoint(Endpoint.Ping));
            fail("Should have thrown");
        } catch (AuthnException ex) {
            assertTrue(ex.getMessage().contains("not found"));
        }
    }

    @Test
    public void wrongPassword() throws AuthzException {
        try {
            authenticationService.authenticate(new AuthnRequest()
                    .setUsername("allan@irunninglog.com")
                    .setPassword("wrong")
                    .setEndpoint(Endpoint.Ping));
            fail("Should have thrown");
        } catch (AuthnException ex) {
            assertTrue(ex.getMessage().contains("don't match"));
        }
    }

}
