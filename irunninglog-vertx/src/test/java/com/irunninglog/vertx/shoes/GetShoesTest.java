package com.irunninglog.vertx.shoes;

import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.shoes.IShoesService;
import com.irunninglog.mock.MockUser;
import com.irunninglog.vertx.AbstractTest;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class GetShoesTest extends AbstractTest {

    @Override
    protected void afterBefore(TestContext context) {
        super.afterBefore(context);

        IShoesService service = Mockito.mock(IShoesService.class);
        deploy(new GetShoesVerticle(factory(), mapper(), service), context);
    }

    @Test
    public void getShoes(TestContext context) throws AuthnException {
        returnFromAuthentication(new MockUser());

        assertEquals(200, get(context, "/api/shoes"));
    }

}
