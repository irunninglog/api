package com.irunninglog.strava.impl;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.runs.IRun;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IUser;
import com.irunninglog.strava.*;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaActivityUpdate;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.StravaGear;
import javastrava.api.v3.model.reference.StravaActivityType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

public class StravaServiceTest extends AbstractStravaTest implements ApplicationContextAware {

    private ApplicationContext context;
    private IStravaService service;
    private IStravaRemoteApi api;
    private IStravaTokenExchange exchange;

    @Before
    public void before() {
        IStravaSession session = context.getBean(IStravaSession.class);

        service = context.getBean(IStravaService.class);
        api = context.getBean(IStravaRemoteApi.class);
        exchange = context.getBean(IStravaTokenExchange.class);

        IFactory factory = context.getBean(IFactory.class);

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
        Mockito.when(factory.get(IStravaShoe.class)).thenReturn(new StravaShoeImpl());
        Mockito.when(factory.get(IRun.class)).thenReturn(new TestRun());
        Mockito.when(factory.get(IStravaSession.class)).thenReturn(session);
        Mockito.when(factory.get(IStravaRemoteApi.class)).thenReturn(api);

        StravaAthlete athlete = new StravaAthlete();
        athlete.setId(1);
        athlete.setEmail("allan@irunninglog.com");
        StravaGear shoe1 = new StravaGear();
        shoe1.setId("shoe_one");
        shoe1.setDistance(100F);
        shoe1.setName("Alpha");
        shoe1.setBrandName("Mizuno");
        shoe1.setModelName("Wave Inspire 13");
        shoe1.setDescription("foo");
        shoe1.setPrimary(Boolean.TRUE);
        athlete.setShoes(Collections.singletonList(shoe1));
        Mockito.when(api.getAuthenticatedAthlete()).thenReturn(athlete);

        Mockito.when(api.getGear("shoe_one")).thenReturn(shoe1);
    }

    @Test
    public void userFromCode() throws AuthnException {
        StravaAthlete athlete = new StravaAthlete();
        athlete.setId(1);
        athlete.setEmail("allan@irunninglog.com");
        Token token = new Token();
        token.setAthlete(athlete);
        token.setToken("token");

        Mockito.when(exchange.token(any(String.class))).thenReturn(token);

        IUser user = service.userFromCode("foo");
        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("allan@irunninglog.com", user.getUsername());
        assertEquals("token", user.getToken());
    }

    @Test
    public void userFromToken() throws AuthnException {
        IUser user = service.userFromToken("foo");
        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("allan@irunninglog.com", user.getUsername());
        assertEquals("foo", user.getToken());
    }

    @Test
    public void athlete() throws AuthnException {
        IUser user = service.userFromToken("foo");

        assertNotNull(user);

        IStravaAthlete stravaAthlete = service.athlete(user);
        assertNotNull(stravaAthlete);
    }

    @Test
    public void runs() throws AuthnException {
        IUser user = service.userFromToken("foo");

        StravaActivity activity = new StravaActivity();
        activity.setType(StravaActivityType.RUN);
        activity.setId(3);
        activity.setStartDate(ZonedDateTime.now());
        activity.setMovingTime(0);
        activity.setDistance(1F);

        Mockito.when(api.listAuthenticatedAthleteActivities(1, 200)).thenReturn(new StravaActivity[] {activity});
        Mockito.when(api.listAuthenticatedAthleteActivities(2, 200)).thenReturn(new StravaActivity[] {});

        List<IRun> runs = service.runs(user);
        assertNotNull(runs);
        assertEquals(1, runs.size());
    }

    @Test
    public void shoes() throws AuthnException {
        IUser user = service.userFromToken("foo");

        List<IStravaShoe> shoes = service.shoes(user);
        assertNotNull(shoes);
        assertEquals(1, shoes.size());

        IStravaShoe one = shoes.get(0);
        assertEquals("shoe_one", one.getId());
        assertEquals("Alpha", one.getName());
        assertEquals("Mizuno", one.getBrand());
        assertEquals("Wave Inspire 13", one.getModel());
        assertEquals("foo", one.getDescription());
        assertEquals(100, one.getDistance(), getMargin());
        assertEquals(true, one.isPrimary());
    }

    @Test
    public void create() throws AuthnException {
        IUser user = service.userFromToken("foo");

        ZonedDateTime time = ZonedDateTime.now();

        IRun before = new TestRun();
        before.setId(-1);
        before.setName("name");
        before.setDuration(3600);
        before.setDistance("8");
        before.setShoes("shoes");
        before.setStartTime(time.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        StravaGear gear = new StravaGear();
        gear.setId("shoes");

        StravaActivity activity = new StravaActivity();
        activity.setId(1);
        activity.setName("name");
        activity.setMovingTime(3600);
        activity.setDistance(8F);
        activity.setGear(gear);
        activity.setStartDate(time);

        Mockito.when(api.create(any(StravaActivity.class))).thenReturn(activity);

        IRun after = service.create(user, before);
        assertNotEquals(before.getId(), after.getId());
        assertEquals(before.getName(), after.getName());
        assertEquals(before.getShoes(), after.getShoes());
        assertEquals(before.getDuration(), after.getDuration());
        assertEquals(before.getDistance(), after.getDistance());
        assertEquals(before.getStartTime(), after.getStartTime());
    }

    @Test
    public void update() throws AuthnException {
        IUser user = service.userFromToken("foo");

        ZonedDateTime time = ZonedDateTime.now();

        IRun before = new TestRun();
        before.setId(1);
        before.setName("name");
        before.setDuration(3600);
        before.setDistance("8");
        before.setShoes("shoes");
        before.setStartTime(time.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        StravaGear gear = new StravaGear();
        gear.setId("shoes");

        StravaActivity activity = new StravaActivity();
        activity.setId(1);
        activity.setName("name");
        activity.setMovingTime(3600);
        activity.setDistance(8F);
        activity.setGear(gear);
        activity.setStartDate(time);
        Mockito.when(api.update(any(Integer.class), any(StravaActivityUpdate.class))).thenReturn(activity);

        IRun after = service.update(user, before);
        assertEquals(before.getId(), after.getId());
        assertEquals(before.getName(), after.getName());
        assertEquals(before.getShoes(), after.getShoes());
        assertEquals(before.getDuration(), after.getDuration());
        assertEquals(before.getDistance(), after.getDistance());
        assertEquals(before.getStartTime(), after.getStartTime());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

}
