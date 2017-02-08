package com.irunninglog.spring.dashboard;

import com.irunninglog.api.dashboard.IProgressInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
public class DashboardProgressServiceTest extends AbstractDashboardServicesTest {

    @Autowired
    private DashboardProgressService progressService;

    @Test
    public void noProgress() {
        assertEquals(3, progressService.progress(profileEntity, 300).size());
    }

    @Test
    public void lastYear() {
        profileEntity.setYearlyTarget(1500);
        saveWorkout(LocalDate.now().minusYears(1), 10);

        assertEquals(4, progressService.progress(profileEntity, 300).size());
    }

    @Test
    public void thisWeek() {
        profileEntity.setWeeklyTarget(30);
        profileEntity.setMonthlyTarget(100);

        saveWorkout(LocalDate.now(), 10);
        saveWorkout(LocalDate.now(), 10);
        saveWorkout(LocalDate.now(), 11);

        Collection<IProgressInfo> progress = progressService.progress(profileEntity, 300);
        Iterator<IProgressInfo> iterator = progress.iterator();

        IProgressInfo thisWeek = iterator.next();
        assertTrue(thisWeek.getTextTwo().contains("100%"));

        IProgressInfo thisMonth = iterator.next();
        assertTrue(thisMonth.getTextTwo().contains("31%"));

        IProgressInfo thisYear = iterator.next();
        assertEquals("31 mi", thisYear.getTextTwo());
    }

}
