package com.irunninglog.spring.report;

import com.irunninglog.api.Progress;
import com.irunninglog.api.Unit;
import com.irunninglog.api.report.IDataPoint;
import com.irunninglog.api.report.IDataSet;
import com.irunninglog.api.report.IReportService;
import com.irunninglog.spring.AbstractTest;
import com.irunninglog.spring.data.RouteEntity;
import com.irunninglog.spring.data.RunEntity;
import com.irunninglog.spring.data.ShoeEntity;
import com.irunninglog.spring.profile.ProfileEntity;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ReportServiceTest extends AbstractTest {

    private IReportService reportService;
    private ProfileEntity profileEntity;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        this.reportService = applicationContext.getBean(IReportService.class);
        profileEntity = saveProfile("allan@irunninglog.com", "password");
    }

    @Test
    public void  mileageByMonth() {
        saveWorkout(LocalDate.now(), 10, profileEntity);
        saveWorkout(LocalDate.now().minusMonths(1).minusMonths(1), 10, profileEntity);
        saveWorkout(LocalDate.now().minusMonths(2).minusMonths(1), 10, profileEntity);
        saveWorkout(LocalDate.now().minusMonths(3).minusMonths(1), 10, profileEntity);
        saveWorkout(LocalDate.now().minusMonths(4).minusMonths(1), 10, profileEntity);
        saveWorkout(LocalDate.now().minusMonths(5).minusMonths(1), 10, profileEntity);
        saveWorkout(LocalDate.now().minusMonths(6).minusMonths(1), 10, profileEntity);
        saveWorkout(LocalDate.now().minusMonths(7).minusMonths(1), 10, profileEntity);
        saveWorkout(LocalDate.now().minusMonths(8).minusMonths(1), 10, profileEntity);
        saveWorkout(LocalDate.now().minusMonths(9).minusMonths(1), 10, profileEntity);
        saveWorkout(LocalDate.now().minusMonths(10).minusMonths(1), 10, profileEntity);
        saveWorkout(LocalDate.now().minusMonths(11).minusMonths(1), 10, profileEntity);
        saveWorkout(LocalDate.now().minusMonths(12).minusMonths(1), 10, profileEntity);

        assertNotNull(reportService.mileageByMonth(profileEntity.getId()));
    }

    @Test
    public void  mileageByRoute() {
        RouteEntity route = saveRoute("One", Boolean.TRUE, profileEntity);
        saveWorkout(LocalDate.now(), 10, 0, profileEntity, route, null, null);

        assertNotNull(reportService.mileageByRoute(profileEntity.getId()));
    }

    @Test
    public void  mileageByRun() {
        RunEntity run = saveRun("One", Boolean.TRUE, profileEntity);
        saveWorkout(LocalDate.now(), 10, 0, profileEntity, null, run, null);

        IDataSet dataSet = reportService.mileageByRun(profileEntity.getId());
        assertEquals(String.valueOf(profileEntity.getId()), dataSet.getKey());
        assertEquals(Unit.ENGLISH, dataSet.getUnits());

        IDataPoint dataPoint = dataSet.getData().iterator().next();
        assertEquals("10.00", dataPoint.getValue());
        assertEquals(Progress.NONE, dataPoint.getProgress());
    }

    @Test
    public void  mileageByShoe() {
        ShoeEntity shoe = saveShoe("One", Boolean.TRUE, profileEntity);
        saveWorkout(LocalDate.now(), 10, 0, profileEntity, null, null, shoe);

        assertNotNull(reportService.mileageByShoe(profileEntity.getId()));
    }

}
