package com.irunninglog.spring.profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.irunninglog.api.Gender;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class DeserializeProfileTest extends AbstractTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void gender() throws IOException {
        assertEquals(null, mapper.readValue("{\"gender\":null}", Profile.class).getGender());
        assertEquals(Gender.MALE, mapper.readValue("{\"gender\":\"Male\"}", Profile.class).getGender());
        assertEquals(Gender.FEMALE, mapper.readValue("{\"gender\":\"Female\"}", Profile.class).getGender());
    }

}
