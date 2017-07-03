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

import java.util.Collections;
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
        IShoe shoe = factory.get(IShoe.class)
                .setId("id")
                .setName("name")
                .setBrand("brand")
                .setModel("model")
                .setDesription("description")
                .setDistance(100)
                .setPrimary(true);

        Mockito.when(stravaService.shoes(any(IUser.class))).thenReturn(Collections.singletonList(shoe));

        List<IShoe> shoes = shoesService.getShoes(null);
        assertNotNull(shoes);
        assertEquals(1, shoes.size());

        IShoe after = shoes.get(0);
        assertEquals("id", after.getId());
        assertEquals("name", after.getName());
        assertEquals("brand", after.getBrand());
        assertEquals("model", after.getModel());
        assertEquals("description", after.getDescription());
        assertEquals(100, after.getDistance(), 1E-9);
        assertTrue(after.isPrimary());
    }

}
