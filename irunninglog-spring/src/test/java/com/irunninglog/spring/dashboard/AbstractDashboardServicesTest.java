package com.irunninglog.spring.dashboard;

import com.irunninglog.spring.AbstractTest;
import com.irunninglog.spring.profile.ProfileEntity;
import org.springframework.context.ApplicationContext;

abstract class AbstractDashboardServicesTest extends AbstractTest {

    ProfileEntity profileEntity;

    @Override
    public final void afterBefore(ApplicationContext context) {
        profileEntity = saveProfile("allan@irunninglog.com");

        afterAfterBefore(context);
    }

    protected abstract void afterAfterBefore(ApplicationContext context);

}
