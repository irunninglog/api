package com.irunninglog.spring.runs;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.runs.IRun;
import com.irunninglog.api.runs.IRunsService;
import com.irunninglog.api.security.IUser;
import com.irunninglog.spring.AbstractTest;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

@Ignore
public class RunsServiceTest extends AbstractTest {

    private static final String TIME = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

    private IFactory factory;
    private IRunsService service;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) throws Exception {
        super.afterBefore(applicationContext);

        factory = applicationContext.getBean(IFactory.class);
        service = applicationContext.getBean(IRunsService.class);

        IRun run = factory.get(IRun.class);
        run.setId(1);
        run.setName("name");
        run.setStartTime(TIME);
        run.setDistance("1");
        run.setDuration(3600);
        run.setShoes("shoes_id");
    }

    @Test
    public void create() {
        IRun before = factory.get(IRun.class);
        before.setId(-1);
        before.setName("name");
        before.setStartTime(TIME);
        before.setDistance("1");
        before.setDuration(3600);
        before.setShoes("shoes_id");

        IRun after = service.create(factory.get(IUser.class), before);

        assertEquals(1, after.getId());
        assertEquals(before.getName(), after.getName());
        assertEquals(before.getStartTime(), after.getStartTime());
        assertEquals(before.getDistance(), after.getDistance());
        assertEquals(before.getDuration(), after.getDuration());
        assertEquals(before.getShoes(), after.getShoes());
    }

    @Test
    public void update() {
        IRun before = factory.get(IRun.class);
        before.setId(-1);
        before.setName("name");
        before.setStartTime(TIME);
        before.setDistance("1");
        before.setDuration(3600);
        before.setShoes("shoes_id");

        IRun after = service.update(factory.get(IUser.class), before);

        assertEquals(1, after.getId());
        assertEquals(before.getName(), after.getName());
        assertEquals(before.getStartTime(), after.getStartTime());
        assertEquals(before.getDistance(), after.getDistance());
        assertEquals(before.getDuration(), after.getDuration());
        assertEquals(before.getShoes(), after.getShoes());
    }

}
