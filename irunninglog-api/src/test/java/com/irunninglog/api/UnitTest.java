package com.irunninglog.api;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class UnitTest {

    @Test
    public void sanity() {
        assertNotNull(Unit.ENGLISH);
        assertNotNull(Unit.METRIC);
    }

}
