package com.irunninglog.vertx;

import io.vertx.ext.unit.TestContext;
import org.junit.Test;

public class BodySizeTest extends AbstractHandlerTest {

    @Override
    protected void afterBefore(TestContext context) throws Exception {

    }

    @Test
    public void bodyTooLarge(TestContext context) {
        String contents = "";
        for (int i = 0; i < 1025; i++) {
            contents += "1";
        }

        context.assertEquals(413, put(context, "/foo", TOKEN, contents));
    }

}
