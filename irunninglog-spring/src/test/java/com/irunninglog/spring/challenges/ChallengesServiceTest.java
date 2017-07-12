package com.irunninglog.spring.challenges;

import com.irunninglog.api.Progress;
import com.irunninglog.api.challenges.IChallenge;
import com.irunninglog.api.challenges.IChallengesService;
import com.irunninglog.api.security.IUser;
import com.irunninglog.spring.AbstractTest;
import com.irunninglog.strava.IStravaRun;
import com.irunninglog.strava.IStravaService;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;

public class ChallengesServiceTest extends AbstractTest {

    private IChallengesService service;
    private IStravaService stravaService;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        service = applicationContext.getBean(IChallengesService.class);
        stravaService = applicationContext.getBean(IStravaService.class);
    }

    @Test
    public void get() {
        IStravaRun run = Mockito.mock(IStravaRun.class);
        Mockito.when(run.getDistance()).thenReturn(1.60934F * 1000000);
        Mockito.when(stravaService.runs(any(IUser.class))).thenReturn(Collections.singletonList(run));

        List<IChallenge> challenges = service.getChallenges(null);

        assertNotNull(challenges);
        assertEquals(6, challenges.size());

        expect(challenges.get(0), "New York to Boston", "Driving distance", "215.1 mi", "215.1 mi", 100, Progress.GOOD);
        expect(challenges.get(1), "London to Rome", "Driving distance", "1,168.3 mi", "1,000 mi", 85, Progress.GOOD);
        expect(challenges.get(2), "Appalachain Trail", "Official website distance", "2,190 mi", "1,000 mi", 45, Progress.OK);
        expect(challenges.get(3), "New York to Los Angeles", "Driving distance", "2,776.8 mi", "1,000 mi", 36, Progress.BAD);
        expect(challenges.get(4), "Around the World", "Distance at the equator", "24,873.5 mi", "1,000 mi", 4, Progress.BAD);
        expect(challenges.get(5), "From the Earth to the Moon", "Average distance", "238,855 mi", "1,000 mi", 0, Progress.BAD);
    }

    private void expect(IChallenge challenge, String name, String description, String total, String done, int percentage, Progress progress) {
        assertEquals(name, challenge.getName());
        assertEquals(description, challenge.getDescription());
        assertEquals(total, challenge.getDistanceTotal());
        assertEquals(done, challenge.getDistanceDone());
        assertEquals(percentage, challenge.getPercentage());
        assertEquals(progress, challenge.getProgress());
    }

}
