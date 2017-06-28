package com.irunninglog.spring.streaks;

import com.irunninglog.api.streaks.IStreaks;
import com.irunninglog.api.streaks.IStreaksService;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertNotNull;

public class StreaksServiceTest extends AbstractTest {

    private IStreaksService streaksService;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        streaksService = applicationContext.getBean(IStreaksService.class);
    }

    @Test
    public void getStreaks() {
        IStreaks streaks = streaksService.getStreaks(null);
        assertNotNull(streaks);
    }
}
