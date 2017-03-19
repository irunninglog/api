package com.irunninglog.api;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class PrivacyTest {

    @Test
    public void sanity() {
        assertNotNull(Privacy.PRIVATE);
        assertNotNull(Privacy.PUBLIC);
    }

}
