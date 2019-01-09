package com.irunninglog.spring.security;

import com.irunninglog.api.athletes.IAthlete;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IAuthenticationService;
import com.irunninglog.api.security.IUser;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AuthenticationServiceTest extends AbstractTest {

    private IAuthenticationService authenticationService;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) throws Exception {
        super.afterBefore(applicationContext);

        authenticationService = applicationContext.getBean(IAuthenticationService.class);

        restTemplate.setAthlete(factory.get(IAthlete.class)
                .setId(-2)
                .setEmail("mock@irunninglog.com")
                .setFirstname("Mock")
                .setLastname("User")
                .setAvatar("https://irunninglog.com/profiles/mock"));
    }

    @Test
    public void authenticateToken() throws AuthnException {
        IUser user = authenticationService.authenticateToken("token");
        assertNotNull(user);
        assertEquals("mock@irunninglog.com", user.getUsername());
        assertEquals("token", user.getToken());
        assertEquals(-2, user.getId());
    }

    @Test
    public void authenticateCode() throws AuthnException {
        IUser user = authenticationService.authenticateCode("code");
        assertNotNull(user);
        assertEquals("mock@irunninglog.com", user.getUsername());
        assertEquals("token", user.getToken());
        assertEquals(-2, user.getId());
    }

}
