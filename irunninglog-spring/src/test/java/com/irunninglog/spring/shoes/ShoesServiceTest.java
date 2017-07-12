package com.irunninglog.spring.shoes;

import com.irunninglog.api.Progress;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.shoes.IShoe;
import com.irunninglog.api.shoes.IShoesService;
import com.irunninglog.spring.AbstractTest;
import com.irunninglog.strava.IStravaService;
import com.irunninglog.strava.IStravaShoe;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

public class ShoesServiceTest extends AbstractTest {

    private IShoesService shoesService;
    private IStravaService stravaService;
    private IFactory factory;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        shoesService = applicationContext.getBean(IShoesService.class);
        stravaService = applicationContext.getBean(IStravaService.class);
        factory = applicationContext.getBean(IFactory.class);
    }

    @Test
    public void getShoes() {
        IStravaShoe shoe1 = factory.get(IStravaShoe.class)
                .setId("id1")
                .setName("name1")
                .setBrand("brand1")
                .setModel("model1")
                .setDescription("description1")
                .setDistance(101095.33F)
                .setPrimary(false);

        IStravaShoe shoe2 = factory.get(IStravaShoe.class)
                .setId("id2")
                .setName("name2")
                .setBrand("brand2")
                .setModel("model2")
                .setDescription("description2")
                .setDistance(322995.34F)
                .setPrimary(true);

        IStravaShoe shoe3 = factory.get(IStravaShoe.class)
                .setId("id3")
                .setName("name3")
                .setBrand("brand3")
                .setModel("model3")
                .setDescription("description3")
                .setDistance(804672)
                .setPrimary(false);

        List<IStravaShoe> before = new ArrayList<>();
        before.add(shoe1);
        before.add(shoe2);
        before.add(shoe3);
        Mockito.when(stravaService.shoes(any(IUser.class))).thenReturn(before);

        List<IShoe> shoes = shoesService.getShoes(null);
        assertNotNull(shoes);
        assertEquals(3, shoes.size());

        IShoe after = shoes.get(1);
        assertEquals("id2", after.getId());
        assertEquals("name2", after.getName());
        assertEquals("brand2", after.getBrand());
        assertEquals("model2", after.getModel());
        assertEquals("description2", after.getDescription());
        assertEquals("200.7 mi", after.getDistance());
        assertEquals(40, after.getPercentage());
        assertEquals(Progress.OK, after.getProgress());
        assertTrue(after.isPrimary());
    }

}
