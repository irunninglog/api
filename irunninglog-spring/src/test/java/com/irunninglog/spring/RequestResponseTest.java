package com.irunninglog.spring;

import com.irunninglog.api.IRequest;
import com.irunninglog.api.IResponse;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.security.IUser;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;

import static org.junit.Assert.*;

public class RequestResponseTest extends AbstractTest {

    private IRequest request;
    private IResponse response;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        request = applicationContext.getBean(IRequest.class);
        response = applicationContext.getBean(IResponse.class);
    }

    @Test
    public void request() {
        request.setUser(Mockito.mock(IUser.class));
        request.setMap(new HashMap<>());
        request.setBody("body");
        request.setOffset(100);

        assertNotNull(request.getUser());
        assertNotNull(request.getMap());
        assertEquals(100, request.getOffset());
        assertEquals("body", request.getBody());
        assertTrue(request.toString().contains("offset"));
    }

    @Test
    public void response() {
        response.setBody(new Object());
        response.setStatus(ResponseStatus.ERROR);

        assertNotNull(response.getBody());
        assertEquals(ResponseStatus.ERROR, response.getStatus());
        assertTrue(response.toString().contains("body"));
    }

}
