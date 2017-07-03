package com.irunninglog.spring.shoes;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.shoes.IShoe;
import com.irunninglog.api.shoes.IShoesService;
import com.irunninglog.spring.AbstractTest;
import com.irunninglog.strava.IStravaService;
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
        IShoe shoe1 = factory.get(IShoe.class)
                .setId("id1")
                .setName("name1")
                .setBrand("brand1")
                .setModel("model1")
                .setDesription("description1")
                .setDistance(100)
                .setPrimary(false);

        IShoe shoe2 = factory.get(IShoe.class)
                .setId("id2")
                .setName("name2")
                .setBrand("brand2")
                .setModel("model2")
                .setDesription("description2")
                .setDistance(200)
                .setPrimary(true);

        List<IShoe> before = new ArrayList<>();
        before.add(shoe1);
        before.add(shoe2);
        Mockito.when(stravaService.shoes(any(IUser.class))).thenReturn(before);

        List<IShoe> shoes = shoesService.getShoes(null);
        assertNotNull(shoes);
        assertEquals(2, shoes.size());

        IShoe after = shoes.get(0);
        assertEquals("id2", after.getId());
        assertEquals("name2", after.getName());
        assertEquals("brand2", after.getBrand());
        assertEquals("model2", after.getModel());
        assertEquals("description2", after.getDescription());
        assertEquals(200, after.getDistance(), 1E-9);
        assertTrue(after.isPrimary());
    }

}
