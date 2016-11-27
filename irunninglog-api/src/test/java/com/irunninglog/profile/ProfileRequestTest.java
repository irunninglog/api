package com.irunninglog.profile;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProfileRequestTest {

    @Test
    public void sanity() {
        ProfileRequest request = new ProfileRequest();
        request.setId(2);
        request.setOffset(300);

        assertEquals(2, request.getId());
        assertEquals(300, request.getOffset());
    }

}
