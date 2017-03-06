package com.irunninglog.main.report;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.report.IReportService;
import com.irunninglog.main.AbstractTest;
import com.irunninglog.spring.data.ShoeEntity;
import com.irunninglog.spring.profile.ProfileEntity;
import com.irunninglog.vertx.endpoint.report.GetMileageByMonthVerticle;
import com.irunninglog.vertx.endpoint.report.GetMileageByRouteVerticle;
import com.irunninglog.vertx.endpoint.report.GetMileageByRunVerticle;
import com.irunninglog.vertx.endpoint.report.GetMileageByShoeVerticle;
import io.vertx.core.Verticle;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GetAllReportsTest extends AbstractTest {

    private ProfileEntity profileEntity;
    private String token;

    @Override
    protected Collection<Verticle> verticles(ApplicationContext applicationContext) {
        IReportService reportService = applicationContext.getBean(IReportService.class);
        IFactory factory = applicationContext.getBean(IFactory.class);
        IMapper mapper = applicationContext.getBean(IMapper.class);

        List<Verticle> verticles = new ArrayList<>();
        verticles.add(new GetMileageByMonthVerticle(reportService, factory, mapper));
        verticles.add(new GetMileageByRouteVerticle(reportService, factory, mapper));
        verticles.add(new GetMileageByRunVerticle(reportService, factory, mapper));
        verticles.add(new GetMileageByShoeVerticle(reportService, factory, mapper));
        return verticles;
    }

    @Override
    protected void afterBefore(TestContext context) {
        profileEntity = save("reports@irunninglog.com", "password", "MYPROFILE");
        token = token("reports@irunninglog.com", "password");
    }

    @Test
    public void byMonth(TestContext context) {
        saveWorkout(profileEntity, LocalDate.now(), 10, 60 * 60 * 1000, null, null, null);
        saveWorkout(profileEntity, LocalDate.now().minusMonths(1), 10, 60 * 60 * 1000, null, null, null);
        context.assertEquals(200, get(context, "/profiles/" + profileEntity.getId() + "/reports/mileagebymonth", token));
    }

    @Test
    public void byRoutes(TestContext context) {
        saveRoute(profileEntity, "name", Boolean.TRUE);
        context.assertEquals(200, get(context, "/profiles/" + profileEntity.getId() + "/reports/mileagebyroute", token));
    }

    @Test
    public void byRuns(TestContext context) {
        saveRun(profileEntity, "name", Boolean.TRUE);
        context.assertEquals(200, get(context, "/profiles/" + profileEntity.getId() + "/reports/mileagebyrun", token));
    }

    @Test
    public void byShoes(TestContext context) {
        ShoeEntity shoeEntity = saveShoe(profileEntity, "name", Boolean.TRUE);
        saveWorkout(profileEntity, LocalDate.now(), 10, 60 * 60 * 1000, null, null, shoeEntity);
        context.assertEquals(200, get(context, "/profiles/" + profileEntity.getId() + "/reports/mileagebyshoe", token));
    }

}
