package com.irunninglog.api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ResponseStatusExceptionTest {

    @Test
    public void sanity() {
        try {
            throw new ResponseStatusException(ResponseStatus.ERROR);
        } catch (ResponseStatusException ex) {
            assertEquals(ResponseStatus.ERROR, ex.getResponseStatus());
            assertTrue(ex.toString().contains("500"));
            assertTrue(ex.toString().contains("Unknown ERROR"));
        }
    }

}
