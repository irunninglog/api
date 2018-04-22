package com.irunninglog.vertx.runs;

import com.irunninglog.api.runs.IRun;
import com.irunninglog.api.runs.IRunsService;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.mock.MockUser;
import com.irunninglog.vertx.AbstractTest;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class PostRunTest extends AbstractTest {

    @Override
    protected void afterBefore(TestContext context) {
        super.afterBefore(context);

        Mockito.when(factory().get(IRun.class)).thenReturn(Mockito.mock(IRun.class));

        deploy(new PostRunVerticle(factory(), mapper(), Mockito.mock(IRunsService.class)), context);
    }

    @Test
    public void createRun(TestContext context) throws AuthnException {
        returnFromAuthentication(new MockUser());

        assertEquals(200, post(context, "/api/runs", "{}"));
    }

}
