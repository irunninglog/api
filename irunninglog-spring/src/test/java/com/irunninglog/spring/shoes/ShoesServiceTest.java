package com.irunninglog.spring.shoes;

import com.irunninglog.api.athletes.IAthlete;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.progress.Progress;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.shoes.IShoe;
import com.irunninglog.api.shoes.IShoesService;
import com.irunninglog.spring.AbstractTest;
import com.irunninglog.spring.strava.StravaService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static org.junit.Assert.*;

public class ShoesServiceTest extends AbstractTest {

    private IShoesService shoesService;
    private IUser user;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) throws Exception {
        super.afterBefore(applicationContext);

        shoesService = applicationContext.getBean(IShoesService.class);
        StravaService stravaService = applicationContext.getBean(StravaService.class);
        IFactory factory = applicationContext.getBean(IFactory.class);

        restTemplate.setAthlete(factory.get(IAthlete.class)
                .setId(-1)
                .setEmail("mock@irunninglog.com")
                .setFirstname("Mock")
                .setLastname("User")
                .setAvatar("https://irunninglog.com/profiles/mock"));

        restTemplate.setShoes(factory.get(IShoe.class)
                .setId("shoes1")
                .setName("one")
                .setDescription("desc1")
                .setDistance("160934")
                .setBrand("brand1")
                .setModel("model1"), factory.get(IShoe.class)
                .setId("shoes2")
                .setName("two")
                .setDescription("desc2")
                .setDistance("321869")
                .setBrand("brand2")
                .setModel("model2"));

        user = stravaService.userFromToken("token");

        waitForShoes(user);
    }

    @Test
    public void getShoes() {
        List<IShoe> shoes = shoesService.getShoes(user);
        assertNotNull(shoes);
        assertEquals(2, shoes.size());

        IShoe after = shoes.get(0);
        assertEquals("shoes2", after.getId());
        assertEquals("two", after.getName());
        assertEquals("brand2", after.getBrand());
        assertEquals("model2", after.getModel());
        assertEquals("desc2", after.getDescription());
        assertEquals("200 mi", after.getDistance());
        assertEquals(40, after.getPercentage());
        assertEquals(Progress.OK, after.getProgress());
        assertFalse(after.isPrimary());
    }

}
