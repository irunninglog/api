package com.irunninglog.spring.report;

import com.irunninglog.api.report.IGetDataSetResponse;
import com.irunninglog.api.report.IGetMultiSetResponse;
import com.irunninglog.api.report.IGetReportRequest;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RequestResponseTest extends AbstractTest {

    private ApplicationContext applicationContext;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        this.applicationContext = applicationContext;
    }

    @Test
    public void request() {
        IGetReportRequest request = applicationContext.getBean(IGetReportRequest.class).setProfileId(3);
        assertEquals(3, request.getProfileId());
    }

    @Test
    public void dataSet() {
        IGetDataSetResponse response = applicationContext.getBean(IGetDataSetResponse.class);
        assertNull(response.getBody());
    }

    @Test
    public void multiSet() {
        IGetMultiSetResponse response = applicationContext.getBean(IGetMultiSetResponse.class);
        assertNull(response.getBody());
    }

}
