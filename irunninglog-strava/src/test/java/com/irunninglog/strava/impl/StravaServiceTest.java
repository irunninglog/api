package com.irunninglog.strava.impl;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.shoes.IShoe;
import com.irunninglog.strava.IStravaApi;
import com.irunninglog.strava.IStravaAthlete;
import com.irunninglog.strava.IStravaRun;
import com.irunninglog.strava.IStravaService;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.StravaGear;
import javastrava.api.v3.model.reference.StravaActivityType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class})
public class StravaServiceTest implements ApplicationContextAware {

    private ApplicationContext context;
    private IStravaService service;
    private IStravaApi api;
    private IFactory factory;

    @Before
    public void before() {
        service = context.getBean(IStravaService.class);
        api = context.getBean(IStravaApi.class);
        factory = context.getBean(IFactory.class);

        Mockito.when(factory.get(IUser.class)).thenReturn(new IUser() {

            private long id;
            private String username;
            private String token;

            @Override
            public IUser setId(long id) {
                this.id = id;
                return this;
            }

            @Override
            public IUser setUsername(String username) {
                this.username = username;
                return this;
            }

            @Override
            public IUser setToken(String token) {
                this.token = token;
                return this;
            }

            @Override
            public String getUsername() {
                return username;
            }

            @Override
            public long getId() {
                return id;
            }

            @Override
            public String getToken() {
                return token;
            }
        });

        Mockito.when(factory.get(IStravaAthlete.class)).thenReturn(new StravaAthleteImpl());
        Mockito.when(factory.get(IStravaRun.class)).thenReturn(new StravaRunImpl());
        Mockito.when(factory.get(IShoe.class)).thenReturn(createShoe());
    }

    private IShoe createShoe() {
        return new IShoe() {

            private String id;
            private String name;
            private String brand;
            private String model;
            private String description;
            private float distance;
            private boolean primary;

            @Override
            public String getId() {
                return id;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getBrand() {
                return brand;
            }

            @Override
            public String getModel() {
                return model;
            }

            @Override
            public String getDescription() {
                return description;
            }

            @Override
            public boolean isPrimary() {
                return primary;
            }

            @Override
            public IShoe setId(String id) {
                this.id = id;
                return this;
            }

            @Override
            public IShoe setName(String name) {
                this.name = name;
                return this;
            }

            @Override
            public IShoe setBrand(String brand) {
                this.brand = brand;
                return this;
            }

            @Override
            public IShoe setModel(String model) {
                this.model = model;
                return this;
            }

            @Override
            public IShoe setDesription(String description) {
                this.description = description;
                return this;
            }

            @Override
            public IShoe setPrimary(boolean primary) {
                this.primary = primary;
                return this;
            }

            @Override
            public float getDistance() {
                return distance;
            }

            @Override
            public IShoe setDistance(float distance) {
                this.distance = distance;
                return this;
            }
        };
    }

    @Test
    public void userFromCode() throws AuthnException {
        StravaAthlete athlete = new StravaAthlete();
        athlete.setId(1);
        athlete.setEmail("allan@irunninglog.com");
        Token token = new Token();
        token.setAthlete(athlete);
        token.setToken("token");

        Mockito.when(api.token(any(String.class))).thenReturn(token);

        IUser user = service.userFromCode("foo");
        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("allan@irunninglog.com", user.getUsername());
        assertEquals("token", user.getToken());
    }

    @Test
    public void userFromToken() throws AuthnException {
        StravaAthlete athlete = new StravaAthlete();
        athlete.setId(1);
        athlete.setEmail("allan@irunninglog.com");

        Mockito.when(api.athlete(any(String.class))).thenReturn(athlete);

        IUser user = service.userFromToken("foo");
        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("allan@irunninglog.com", user.getUsername());
        assertEquals("foo", user.getToken());
    }

    @Test
    public void athlete() {
        StravaAthlete athlete = new StravaAthlete();
        athlete.setId(2);
        Mockito.when(api.athlete(any(String.class))).thenReturn(athlete);

        IUser user = factory.get(IUser.class).setToken("bar");

        IStravaAthlete stravaAthlete = service.athlete(user);
        assertNotNull(stravaAthlete);
    }

    @Test
    public void runs() {
        IUser user = factory.get(IUser.class).setToken("bar");

        StravaActivity activity = new StravaActivity();
        activity.setType(StravaActivityType.RUN);
        activity.setId(3);
        activity.setDistance(1F);
        Mockito.when(api.activities(user, 1)).thenReturn(new StravaActivity[] {activity});
        Mockito.when(api.activities(user, 2)).thenReturn(new StravaActivity[0]);

        List<IStravaRun> runs = service.runs(user);
        assertNotNull(runs);
        assertEquals(1, runs.size());
    }

    @Test
    public void shoes() {
        StravaGear shoe1 = new StravaGear();
        shoe1.setId("shoe_one");
        shoe1.setDistance(100F);
        shoe1.setName("Alpha");
        shoe1.setBrandName("Mizuno");
        shoe1.setModelName("Wave Inspire 13");
        shoe1.setDescription("foo");
        shoe1.setPrimary(Boolean.TRUE);

        StravaAthlete athlete = new StravaAthlete();
        athlete.setId(20);
        athlete.setShoes(Collections.singletonList(shoe1));
        Mockito.when(api.athlete(any(String.class))).thenReturn(athlete);

        IUser user = factory.get(IUser.class).setToken("shoes_token");

        List<IShoe> shoes = service.shoes(user);
        assertNotNull(shoes);
        assertEquals(1, shoes.size());

        IShoe one = shoes.get(0);
        assertEquals("shoe_one", one.getId());
        assertEquals("Alpha", one.getName());
        assertEquals("Mizuno", one.getBrand());
        assertEquals("Wave Inspire 13", one.getModel());
        assertEquals("foo", one.getDescription());
        assertEquals(100, one.getDistance(), 1E-9);
        assertEquals(true, one.isPrimary());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

}
