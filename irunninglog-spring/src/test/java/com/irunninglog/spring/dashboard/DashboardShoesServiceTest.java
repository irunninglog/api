package com.irunninglog.spring.dashboard;

import org.junit.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertEquals;

public class DashboardShoesServiceTest extends AbstractDashboardServicesTest {

    private DashboardShoesService shoesService;

    @Test
    public void noShoes() {
        assertEquals(0, shoesService.shoes(profileEntity).size());
    }

    @Test
    public void oneShoe() {
        saveShoe("One", Boolean.FALSE, profileEntity);
        saveShoe("Two", Boolean.TRUE, profileEntity);

        assertEquals(1, shoesService.shoes(profileEntity).size());
    }

    @Test
    public void twoShoes() {
        saveShoe("One", Boolean.TRUE, profileEntity);
        saveShoe("Two", Boolean.TRUE, profileEntity);

        assertEquals(2, shoesService.shoes(profileEntity).size());
    }

    @Override
    protected void afterAfterBefore(ApplicationContext context) {
        shoesService = context.getBean(DashboardShoesService.class);
    }

}
