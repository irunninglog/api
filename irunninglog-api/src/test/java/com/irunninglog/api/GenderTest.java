package com.irunninglog.api;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class GenderTest {

    @Test
    public void sanity() {
        assertNotNull(Gender.MALE);
        assertNotNull(Gender.FEMALE);
    }

}
