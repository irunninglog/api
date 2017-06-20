package com.irunninglog.spring.security;

import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IAuthenticationService;
import com.irunninglog.api.security.IUser;
import com.irunninglog.spring.AbstractTest;
import com.irunninglog.strava.IStravaService;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;

public class AuthenticationServiceTest extends AbstractTest {

    private IAuthenticationService authenticationService;
    private IStravaService stravaService;
    private IUser user;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        authenticationService = applicationContext.getBean(IAuthenticationService.class);
        stravaService = applicationContext.getBean(IStravaService.class);
        user = applicationContext.getBean(IUser.class)
                .setId(1)
                .setUsername("allan@irunninglog.com")
                .setToken("TOKEN");
    }

    @Test
    public void authenticateToken() throws AuthnException {
        Mockito.when(stravaService.userFromToken(any(String.class))).thenReturn(user);

        IUser response = authenticationService.authenticateToken("token");
        assertNotNull(response);
        assertEquals(user.getUsername(), response.getUsername());
        assertEquals(user.getToken(), response.getToken());
        assertEquals(user.getId(), response.getId());
    }

    @Test
    public void authenticateCode() throws AuthnException {
        Mockito.when(stravaService.userFromCode(any(String.class))).thenReturn(user);

        IUser response = authenticationService.authenticateCode("code");
        assertNotNull(response);
        assertEquals(user.getUsername(), response.getUsername());
        assertEquals(user.getToken(), response.getToken());
        assertEquals(user.getId(), response.getId());
    }

}
