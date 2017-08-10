package com.irunninglog.strava.impl;

import com.irunninglog.strava.IStravaRemoteApi;
import com.irunninglog.strava.IStravaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {StravaConfig.class})
public class StravaConfigTest {

    @Autowired
    private IStravaService service;
    @Autowired
    private IStravaRemoteApi api;

    static {
        System.setProperty("strava", "1|foo");
    }

    @Test
    public void sanity() {
        assertNotNull(service);
        assertNotNull(api);
    }

}
