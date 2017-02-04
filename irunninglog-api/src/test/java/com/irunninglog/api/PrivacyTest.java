package com.irunninglog.api;

import com.irunninglog.api.Privacy;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class PrivacyTest {

    @Test
    public void sanity() {
        assertNotNull(Privacy.Private);
        assertNotNull(Privacy.Public);
    }

}
