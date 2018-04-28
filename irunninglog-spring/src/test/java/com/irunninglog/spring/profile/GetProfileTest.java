package com.irunninglog.spring.profile;

import com.irunninglog.api.profile.IProfile;
import com.irunninglog.api.profile.IProfileService;
import com.irunninglog.api.security.IUser;
import com.irunninglog.spring.AbstractTest;
import com.irunninglog.strava.IStravaAthlete;
import com.irunninglog.strava.IStravaService;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;

public class GetProfileTest extends AbstractTest {

    private IProfileService profileService;
    private IStravaService stravaService;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        this.profileService = applicationContext.getBean(IProfileService.class);
        this.stravaService = applicationContext.getBean(IStravaService.class);
    }

    @Test
    public void getProfile() {
        IStravaAthlete athlete = Mockito.mock(IStravaAthlete.class);
        Mockito.when(athlete.getFirstname()).thenReturn("Allan");
        Mockito.when(athlete.getLastname()).thenReturn("Lewis");
        Mockito.when(athlete.getEmail()).thenReturn("allan@irunninglog.com");
        Mockito.when(athlete.getId()).thenReturn(1);
        Mockito.when(athlete.getAvatar()).thenReturn("image.png");

        Mockito.when(stravaService.athlete(any(IUser.class))).thenReturn(athlete);

        IProfile profile = profileService.get(Mockito.mock(IUser.class));
        assertNotNull(profile);
        assertEquals("Allan", profile.getFirstName());
        assertEquals("Lewis", profile.getLastName());
        assertEquals("allan@irunninglog.com", profile.getUsername());
        assertEquals(1L, profile.getId());
        assertEquals("image.png", profile.getAvatar());
    }

}
