package com.irunninglog.spring.identity;

import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.ResponseStatusException;
import com.irunninglog.api.identity.IIdentity;
import com.irunninglog.api.identity.IIdentityService;
import com.irunninglog.spring.AbstractTest;
import com.irunninglog.spring.profile.IProfileEntityRepository;
import com.irunninglog.spring.profile.ProfileEntity;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.*;

public class IdentityServiceTest extends AbstractTest {

    private ProfileEntity existing;
    private IIdentityService identityService;
    private IProfileEntityRepository profileEntityRepository;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        identityService = applicationContext.getBean(IIdentityService.class);
        profileEntityRepository = applicationContext.getBean(IProfileEntityRepository.class);

        existing = saveProfile("existinguser");
    }

    @Test
    public void nonExistent() {
        assertEquals(1, profileEntityRepository.count());

        try {
            identityService.identity("newuser");
            fail("Should have thrown");
        } catch (ResponseStatusException ex) {
            assertEquals(ResponseStatus.UNAUTHORIZED, ex.getResponseStatus());
            assertEquals(1, profileEntityRepository.count());
        }

    }

    @Test
    public void existing() {
        assertEquals(1, profileEntityRepository.count());

        IIdentity identity = identityService.identity("existinguser");
        assertNotNull(identity);
        assertEquals("existinguser", identity.getUsername());
        assertEquals(existing.getId(), identity.getId());

        assertEquals(1, profileEntityRepository.count());
    }

}
