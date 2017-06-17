package com.irunninglog.api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ResponseStatusTest {

    @Test
    public void sanity() {
        assertEquals(200, ResponseStatus.OK.getCode());
        assertEquals("Ok", ResponseStatus.OK.getMessage());

        assertEquals(400, ResponseStatus.BAD.getCode());
        assertEquals("Bad Request", ResponseStatus.BAD.getMessage());

        assertEquals(401, ResponseStatus.UNAUTHENTICATED.getCode());
        assertEquals("Not Authenticated", ResponseStatus.UNAUTHENTICATED.getMessage());

        assertEquals(403, ResponseStatus.UNAUTHORIZED.getCode());
        assertEquals("Not Authorized", ResponseStatus.UNAUTHORIZED.getMessage());

        assertEquals(404, ResponseStatus.NOT_FOUND.getCode());
        assertEquals("Not Found", ResponseStatus.NOT_FOUND.getMessage());

        assertEquals(500, ResponseStatus.ERROR.getCode());
        assertEquals("Unknown Error", ResponseStatus.ERROR.getMessage());
        assertNotNull(ResponseStatus.ERROR.toString());
    }

}
