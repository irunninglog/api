package com.irunninglog.vertx.endpoint;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.data.*;
import com.irunninglog.vertx.Envelope;
import com.irunninglog.vertx.endpoint.data.GetRoutesVerticle;
import com.irunninglog.vertx.endpoint.data.GetRunsVerticle;
import com.irunninglog.vertx.endpoint.data.GetShoesVerticle;
import com.irunninglog.vertx.mock.MockRoutes;
import com.irunninglog.vertx.mock.MockRuns;
import com.irunninglog.vertx.mock.MockShoes;
import com.irunninglog.vertx.mock.MockUser;
import io.vertx.ext.unit.TestContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;

public class GetDataTest extends AbstractVerticleTest {

    @Before
    public void before(TestContext context) {
        IDataService dataService = Mockito.mock(IDataService.class);
        Mockito.when(dataService.routes(any(Long.class))).thenReturn(new MockRoutes());
        Mockito.when(dataService.runs(any(Long.class))).thenReturn(new MockRuns());
        Mockito.when(dataService.shoes(any(Long.class))).thenReturn(new MockShoes());

        rule.vertx().deployVerticle(new GetShoesVerticle(dataService, factory, mapper), context.asyncAssertSuccess());
        rule.vertx().deployVerticle(new GetRunsVerticle(dataService, factory, mapper), context.asyncAssertSuccess());
        rule.vertx().deployVerticle(new GetRoutesVerticle(dataService, factory, mapper), context.asyncAssertSuccess());
    }

    @Test
    public void shoes(TestContext context) {
        Envelope envelope = new Envelope().setUser(new MockUser().setId(1)).setRequest(mapper.encode(factory.get(IGetDataRequest.class).setProfileId(1)));
        rule.vertx().eventBus().<String>send(Endpoint.SHOES_GET.getAddress(), mapper.encode(envelope), context.asyncAssertSuccess(o ->  {
            String s = o.body();
            IGetShoesResponse response = mapper.decode(s, IGetShoesResponse.class);

            context.assertEquals(ResponseStatus.OK, response.getStatus());
            context.assertNotNull(response.getBody());
        }));
    }

    @Test
    public void routes(TestContext context) {
        Envelope envelope = new Envelope().setUser(new MockUser().setId(1)).setRequest(mapper.encode(factory.get(IGetDataRequest.class).setProfileId(1)));
        rule.vertx().eventBus().<String>send(Endpoint.ROUTES_GET.getAddress(), mapper.encode(envelope), context.asyncAssertSuccess(o ->  {
            String s = o.body();
            IGetRoutesResponse response = mapper.decode(s, IGetRoutesResponse.class);

            context.assertEquals(ResponseStatus.OK, response.getStatus());
            context.assertNotNull(response.getBody());
        }));
    }

    @Test
    public void runs(TestContext context) {
        Envelope envelope = new Envelope().setUser(new MockUser().setId(1)).setRequest(mapper.encode(factory.get(IGetDataRequest.class).setProfileId(1)));
        rule.vertx().eventBus().<String>send(Endpoint.RUNS_GET.getAddress(), mapper.encode(envelope), context.asyncAssertSuccess(o ->  {
            String s = o.body();
            IGetRunsResponse response = mapper.decode(s, IGetRunsResponse.class);

            context.assertEquals(ResponseStatus.OK, response.getStatus());
            context.assertNotNull(response.getBody());
        }));
    }

}
