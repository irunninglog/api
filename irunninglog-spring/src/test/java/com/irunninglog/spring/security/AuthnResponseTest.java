package com.irunninglog.spring.security;

import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.IAuthnResponse;
import com.irunninglog.api.security.IUser;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AuthnResponseTest extends AbstractTest {

    private IMapper mapper;

    @Override
    protected final void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        mapper = applicationContext.getBean(IMapper.class);
    }

    @Test
    public void decode() {
        String string = "{\"body\":{\"id\":1,\"username\":\"login@irunninglog.com\",\"authorities\":[\"MYPROFILE\"]},\"status\":\"Ok\"}";
        IAuthnResponse authnResponse = mapper.decode(string, IAuthnResponse.class);
        assertNotNull(authnResponse.getBody());
        assertTrue(authnResponse.getBody() instanceof IUser);
    }

}
