package com.irunninglog.profile;

import com.irunninglog.service.ResponseStatus;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProfileResponseTest {

    @Test
    public void sanity() {
        Profile profile = new Profile();

        ProfileResponse response = new ProfileResponse();
        response.setStatus(ResponseStatus.Ok);
        response.setBody(profile);

        assertEquals(ResponseStatus.Ok, response.getStatus());
        assertEquals(profile, response.getBody());

        assertTrue(response.toString().contains("status"));
        assertTrue(response.toString().contains("body"));
    }

}
