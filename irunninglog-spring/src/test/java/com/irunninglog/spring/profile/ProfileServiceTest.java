package com.irunninglog.spring.profile;

import com.irunninglog.api.ResponseStatusException;
import com.irunninglog.api.profile.IProfileService;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
        assertNotNull(profileService.get(goodId));
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
