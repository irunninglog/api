package com.irunninglog.service;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResponseStatusTest {

    @Test
    public void sanity() {
        assertEquals(200, ResponseStatus.Ok.getCode());
        assertEquals("Ok", ResponseStatus.Ok.getMessage());

        assertEquals(400, ResponseStatus.Bad.getCode());
        assertEquals("Bad Request", ResponseStatus.Bad.getMessage());

        assertEquals(401, ResponseStatus.Unauthnticated.getCode());
        assertEquals("Not Authenticated", ResponseStatus.Unauthnticated.getMessage());

        assertEquals(403, ResponseStatus.Unauthorized.getCode());
        assertEquals("Not Authorized", ResponseStatus.Unauthorized.getMessage());

        assertEquals(404, ResponseStatus.NotFound.getCode());
        assertEquals("Not Found", ResponseStatus.NotFound.getMessage());

        assertEquals(500, ResponseStatus.Error.getCode());
        assertEquals("Unknown Error", ResponseStatus.Error.getMessage());
    }

}
