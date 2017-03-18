package com.irunninglog.vertx.endpoint;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.data.IGetDataRequest;
import com.irunninglog.api.report.IGetDataSetResponse;
import com.irunninglog.api.report.IGetMultiSetResponse;
import com.irunninglog.api.report.IReportService;
import com.irunninglog.vertx.endpoint.report.GetMileageByMonthVerticle;
import com.irunninglog.vertx.endpoint.report.GetMileageByRouteVerticle;
import com.irunninglog.vertx.endpoint.report.GetMileageByRunVerticle;
import com.irunninglog.vertx.endpoint.report.GetMileageByShoeVerticle;
import com.irunninglog.vertx.mock.MockDataSet;
import com.irunninglog.vertx.mock.MockMultiSet;
import io.vertx.ext.unit.TestContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;

public class GetReportsTest extends AbstractVerticleTest {

    @Before
    public void before(TestContext context) {
        IReportService service = Mockito.mock(IReportService.class);
        Mockito.when(service.mileageByMonth(any(Long.class))).thenReturn(new MockMultiSet());
        Mockito.when(service.mileageByRoute(any(Long.class))).thenReturn(new MockDataSet());
        Mockito.when(service.mileageByRun(any(Long.class))).thenReturn(new MockDataSet());
        Mockito.when(service.mileageByShoe(any(Long.class))).thenReturn(new MockDataSet());

        rule.vertx().deployVerticle(new GetMileageByMonthVerticle(service, factory, mapper), context.asyncAssertSuccess());
        rule.vertx().deployVerticle(new GetMileageByRouteVerticle(service, factory, mapper), context.asyncAssertSuccess());
        rule.vertx().deployVerticle(new GetMileageByRunVerticle(service, factory, mapper), context.asyncAssertSuccess());
        rule.vertx().deployVerticle(new GetMileageByShoeVerticle(service, factory, mapper), context.asyncAssertSuccess());
    }

    @Test
    public void mileageByMonth(TestContext context) {
        rule.vertx().eventBus().<String>send(Endpoint.REPORT_MILEAGE_MONTH_GET.getAddress(), mapper.encode(factory.get(IGetDataRequest.class)), context.asyncAssertSuccess(o ->  {
            String s = o.body();
            IGetMultiSetResponse response = mapper.decode(s, IGetMultiSetResponse.class);

            context.assertEquals(ResponseStatus.OK, response.getStatus());
            context.assertNotNull(response.getBody());
        }));
    }

    @Test
    public void mileageByShoe(TestContext context) {
        rule.vertx().eventBus().<String>send(Endpoint.REPORT_MILEAGE_SHOE_GET.getAddress(), mapper.encode(factory.get(IGetDataRequest.class)), context.asyncAssertSuccess(o ->  {
            String s = o.body();
            IGetDataSetResponse response = mapper.decode(s, IGetDataSetResponse.class);

            context.assertEquals(ResponseStatus.OK, response.getStatus());
            context.assertNotNull(response.getBody());
        }));
    }

    @Test
    public void mileageByRun(TestContext context) {
        rule.vertx().eventBus().<String>send(Endpoint.REPORT_MILEAGE_RUN_GET.getAddress(), mapper.encode(factory.get(IGetDataRequest.class)), context.asyncAssertSuccess(o ->  {
            String s = o.body();
            IGetDataSetResponse response = mapper.decode(s, IGetDataSetResponse.class);

            context.assertEquals(ResponseStatus.OK, response.getStatus());
            context.assertNotNull(response.getBody());
        }));
    }

    @Test
    public void mileageByRoute(TestContext context) {
        rule.vertx().eventBus().<String>send(Endpoint.REPORT_MILEAGE_ROUTE_GET.getAddress(), mapper.encode(factory.get(IGetDataRequest.class)), context.asyncAssertSuccess(o ->  {
            String s = o.body();
            IGetDataSetResponse response = mapper.decode(s, IGetDataSetResponse.class);

            context.assertEquals(ResponseStatus.OK, response.getStatus());
            context.assertNotNull(response.getBody());
        }));
    }

}
