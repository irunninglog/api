package com.irunninglog.spring.data;

import com.irunninglog.api.data.IDataService;
import com.irunninglog.api.data.IRoute;
import com.irunninglog.api.data.IRoutes;
import com.irunninglog.api.data.IShoe;
import com.irunninglog.spring.AbstractTest;
import com.irunninglog.spring.profile.ProfileEntity;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class DataServiceTest extends AbstractTest {

    private IDataService dataService;
    private ProfileEntity profileEntity;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        dataService = applicationContext.getBean(IDataService.class);
        profileEntity = saveProfile("allan@irunninglog.com", "password");
    }

    @Test
    public void noShoes() {
        assertEquals(0, dataService.shoes(profileEntity.getId()).getShoes().size());
    }

    @Test
    public void oneShoe() {
        saveShoe("One", Boolean.TRUE, profileEntity);

        IShoe shoe = dataService.shoes(profileEntity.getId()).getShoes().iterator().next();
        assertEquals("--", shoe.getMax());
    }

    @Test
    public void fourShoes() {
        saveShoe("One", LocalDate.now(), Boolean.TRUE, profileEntity);
        saveShoe("Two", Boolean.TRUE, profileEntity);
        saveShoe("Three", LocalDate.now(), Boolean.TRUE, profileEntity);
        saveShoe("Four", LocalDate.now(), Boolean.TRUE, profileEntity);
        saveShoe("Five", Boolean.TRUE, profileEntity);

        assertEquals(5, dataService.shoes(profileEntity.getId()).getShoes().size());
    }

    @Test
    public void noRuns() {
        assertEquals(0, dataService.runs(profileEntity.getId()).getRuns().size());
    }

    @Test
    public void twoRuns() {
        saveRun("One", Boolean.TRUE, profileEntity);
        saveRun("Two", Boolean.TRUE, profileEntity);

        assertEquals(2, dataService.runs(profileEntity.getId()).getRuns().size());
    }

    @Test
    public void noRoutes() {
        assertEquals(0, dataService.routes(profileEntity.getId()).getRoutes().size());
    }

    @Test
    public void oneRoute() {
        saveRoute("One", Boolean.TRUE, profileEntity);

        IRoutes routes = dataService.routes(profileEntity.getId());
        IRoute route = routes.getRoutes().iterator().next();

        assertNotNull(route.getId());
        assertEquals("One", route.getName());
        assertNull(route.getDescription());
        assertTrue(route.isDashboard());
    }

    @Test
    public void twoRoutes() {
        saveRoute("One", Boolean.TRUE, profileEntity);
        saveRoute("Two", Boolean.TRUE, profileEntity);

        assertEquals(2, dataService.routes(profileEntity.getId()).getRoutes().size());
    }

}
