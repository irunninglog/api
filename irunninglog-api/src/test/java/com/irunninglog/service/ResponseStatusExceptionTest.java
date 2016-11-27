package com.irunninglog.service;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ResponseStatusExceptionTest {

    @Test
    public void sanity() {
        try {
            throw new ResponseStatusException(ResponseStatus.Error);
        } catch (ResponseStatusException ex) {
            assertEquals(ResponseStatus.Error, ex.getResponseStatus());
            assertTrue(ex.toString().contains("500"));
            assertTrue(ex.toString().contains("Unknown Error"));
        }
    }

}
