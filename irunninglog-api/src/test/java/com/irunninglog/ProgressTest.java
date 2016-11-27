package com.irunninglog;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ProgressTest {

    @Test
    public void sanity() {
        assertNotNull(Progress.Bad);
        assertNotNull(Progress.Good);
        assertNotNull(Progress.Ok);
        assertNotNull(Progress.None);
    }

}
