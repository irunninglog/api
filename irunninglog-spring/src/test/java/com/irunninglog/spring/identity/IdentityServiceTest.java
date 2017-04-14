package com.irunninglog.spring.identity;

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

        existing = saveProfile("existinguser", "foo");
    }

    @Test
    public void created() {
        assertEquals(1, profileEntityRepository.count());

        IIdentity identity = identityService.identity("newuser");
        assertNotNull(identity);
        assertEquals("newuser", identity.getUsername());
        assertTrue(identity.getId() > 0);
        assertTrue(identity.isCreated());

        assertEquals(2, profileEntityRepository.count());
    }

    @Test
    public void existing() {
        assertEquals(1, profileEntityRepository.count());

        IIdentity identity = identityService.identity("existinguser");
        assertNotNull(identity);
        assertEquals("existinguser", identity.getUsername());
        assertEquals(existing.getId(), identity.getId());
        assertFalse(identity.isCreated());

        assertEquals(1, profileEntityRepository.count());
    }

}
