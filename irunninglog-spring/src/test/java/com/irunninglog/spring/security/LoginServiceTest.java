package com.irunninglog.spring.security;

import com.irunninglog.api.security.ILogin;
import com.irunninglog.api.security.ILoginService;
import com.irunninglog.api.security.IUser;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LoginServiceTest extends AbstractTest {

    private ILoginService loginService;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        loginService = applicationContext.getBean(ILoginService.class);
    }

    @Test
    public void login() {
        IUser user = new User()
                .setId(1)
                .setUsername("allan@irunninglog.com")
                .setAuthorities(Collections.singletonList("MYPROFILE"));

        ILogin login = loginService.login(user);
        assertNotNull(login);
        assertEquals(1, login.getId());
        assertEquals("allan@irunninglog.com", login.getName());
        assertEquals(1, login.getAuthorities().size());
    }

}
