package com.irunninglog.spring.data;

import com.irunninglog.api.data.IGetDataRequest;
import com.irunninglog.api.data.IGetRoutesResponse;
import com.irunninglog.api.data.IGetRunsResponse;
import com.irunninglog.api.data.IGetShoesResponse;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DataRequestResponseTest extends AbstractTest {

    private ApplicationContext context;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        context = applicationContext;
    }

    @Test
    public void request() {
        IGetDataRequest request = context.getBean(IGetDataRequest.class).setProfileId(2).setOffset(300);

        assertEquals(2, request.getProfileId());
        assertEquals(300, request.getOffset());
    }

    @Test
    public void responses() {
        IGetShoesResponse shoes = context.getBean(IGetShoesResponse.class);
        assertNull(shoes.getBody());

        IGetRoutesResponse routes = context.getBean(IGetRoutesResponse.class);
        assertNull(routes.getBody());

        IGetRunsResponse runs = context.getBean(IGetRunsResponse.class);
        assertNull(runs.getBody());
    }

}
