package com.irunninglog.spring.profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.irunninglog.api.Unit;
import com.irunninglog.api.profile.IProfile;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertTrue;

public class SerializeProfileTest extends AbstractTest {

    private IProfile profile;

    private final ObjectMapper mapper = new ObjectMapper();


    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        profile = applicationContext.getBean(IProfile.class);
    }

    @Test
    public void unit() throws Exception {
        assertTrue(mapper.writeValueAsString(profile).contains("\"preferredUnits\":null"));

        profile.setPreferredUnits(Unit.ENGLISH);
        assertTrue(mapper.writeValueAsString(profile).contains("\"preferredUnits\":\"ENGLISH\""));

        profile.setPreferredUnits(Unit.METRIC);
        assertTrue(mapper.writeValueAsString(profile).contains("\"preferredUnits\":\"METRIC\""));
    }

}
