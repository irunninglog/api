package com.irunninglog.spring.streaks;

import com.irunninglog.api.Progress;
import com.irunninglog.api.streaks.IStreak;
import com.irunninglog.api.streaks.IStreaks;
import com.irunninglog.api.streaks.IStreaksService;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StreaksServiceTest extends AbstractTest {

    private IStreaksService streaksService;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        streaksService = applicationContext.getBean(IStreaksService.class);
    }

    @Test
    public void getStreaks() {
        IStreaks streaks = streaksService.getStreaks(null);
        assertNotNull(streaks);

        IStreak longest = streaks.getLongest();
        assertNotNull(longest);
        assertEquals("2015-01-01", longest.getStartDate());
        assertEquals("2015-10-01", longest.getEndDate());
        assertEquals(274, longest.getDays());
        assertEquals(274, longest.getRuns());
        assertEquals(100, longest.getPercentage());
        assertEquals(Progress.GOOD, longest.getProgress());

        IStreak current = streaks.getCurrent();
        assertNotNull(current);
        assertEquals("2017-06-10", current.getStartDate());
        assertEquals("2017-06-12", current.getEndDate());
        assertEquals(3, current.getDays());
        assertEquals(3, current.getRuns());
        assertEquals(1, current.getPercentage());
        assertEquals(Progress.BAD, current.getProgress());

        IStreak thisYear = streaks.getThisYear();
        assertNotNull(thisYear);
        assertEquals("2016-12-04", thisYear.getStartDate());
        assertEquals("2017-06-02", thisYear.getEndDate());
        assertEquals(181, thisYear.getDays());
        assertEquals(184, thisYear.getRuns());
        assertEquals(68, thisYear.getPercentage());
        assertEquals(Progress.OK, thisYear.getProgress());
    }
}
