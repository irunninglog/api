package com.irunninglog.api;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class AccessControlTest {

    @Test
    public void sanity() {
        assertNotNull(AccessControl.AUTHENTICATED);
    }


}
