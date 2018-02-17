package com.irunninglog.spring.util;

import com.irunninglog.spring.AbstractTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertEquals;

public class DistanceServiceTest extends AbstractTest {

    private DistanceService distanceService;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        this.distanceService = applicationContext.getBean(DistanceService.class);
    }

    @Test
    public void testRounding() {
        assertEquals("144.9 mi", distanceService.mileage(233336.600341796875F));
        assertEquals("1,000 mi", distanceService.mileage(1609344.1F));
    }

}
