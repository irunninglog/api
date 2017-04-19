package com.irunninglog.spring.profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.irunninglog.api.Unit;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DeserializeProfileTest extends AbstractTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void unit() throws Exception {
        assertEquals(null, mapper.readValue("{\"preferredUnits\":null}", Profile.class).getPreferredUnits());
        assertEquals(Unit.ENGLISH, mapper.readValue("{\"preferredUnits\":\"English\"}", Profile.class).getPreferredUnits());
        assertEquals(Unit.METRIC, mapper.readValue("{\"preferredUnits\":\"Metric\"}", Profile.class).getPreferredUnits());
    }

}
