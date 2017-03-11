package com.irunninglog.spring.profile;

import com.irunninglog.api.ResponseStatusException;
import com.irunninglog.api.profile.IProfile;
import com.irunninglog.api.profile.IProfileService;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.*;

public class ProfileServiceTest extends AbstractTest {

    private IProfileService profileService;

    private long goodId;
    private long badId;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        profileService = applicationContext.getBean(IProfileService.class);

        ProfileEntity profileEntity = saveProfile("allan@irunninglog.com", "password");

        goodId = profileEntity.getId();
        badId = profileEntity.getId() + 1;
    }

    @Test
    public void good() {
        IProfile profile = profileService.get(goodId);
        assertNotNull(profile);
        assertEquals(goodId, profile.getId());
        assertEquals("allan@irunninglog.com", profile.getEmail());
        assertNotNull(profile.getFirstName());
        assertNotNull(profile.getLastName());
        assertNotNull(profile.getBirthday());
        assertNotNull(profile.getGender());
        assertNotNull(profile.getPreferredUnits());
        assertNotNull(profile.getWeekStart());
        assertEquals(0, profile.getWeeklyTarget(), 1E-9);
        assertEquals(0, profile.getMonthlyTarget(), 1E-9);
        assertEquals(0, profile.getYearlyTarget(), 1E-9);
        assertEquals(-1, profile.getDefaultRouteId());
        assertEquals(-1, profile.getDefaultRunId());
        assertEquals(-1, profile.getDefaultShoeId());
    }

    @Test
    public void bad() {
        try {
            profileService.get(badId);
            fail("Should have thrown");
        } catch (ResponseStatusException ex) {
            assertTrue(Boolean.TRUE);
        }
    }

}
