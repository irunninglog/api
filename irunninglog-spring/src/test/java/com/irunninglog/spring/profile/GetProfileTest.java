package com.irunninglog.spring.profile;

import com.irunninglog.api.athletes.IAthlete;
import com.irunninglog.api.profile.IProfile;
import com.irunninglog.api.profile.IProfileService;
import com.irunninglog.api.security.IUser;
import com.irunninglog.spring.AbstractTest;
import com.irunninglog.spring.strava.StravaApiService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GetProfileTest extends AbstractTest {

    private IProfileService profileService;
    private IUser user;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) throws Exception {
        super.afterBefore(applicationContext);

        this.profileService = applicationContext.getBean(IProfileService.class);
        StravaApiService stravaService = applicationContext.getBean(StravaApiService.class);

        restTemplate.setAthlete(factory.get(IAthlete.class)
                .setId(-1)
                .setEmail("mock@irunninglog.com")
                .setFirstname("Mock")
                .setLastname("User")
                .setAvatar("https://irunninglog.com/profiles/mock"));

        user = stravaService.userFromToken("token");
    }

    @Test
    public void getProfile() {
        IProfile profile = profileService.get(user);
        assertNotNull(profile);
        assertEquals("Mock", profile.getFirstName());
        assertEquals("User", profile.getLastName());
        assertEquals("mock@irunninglog.com", profile.getUsername());
        assertEquals(-1, profile.getId());
        assertEquals("https://irunninglog.com/profiles/mock", profile.getAvatar());
    }

}
