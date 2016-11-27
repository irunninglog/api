package com.irunninglog;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class GenderTest {

    @Test
    public void sanity() {
        assertNotNull(Gender.Male);
        assertNotNull(Gender.Female);
    }

}
