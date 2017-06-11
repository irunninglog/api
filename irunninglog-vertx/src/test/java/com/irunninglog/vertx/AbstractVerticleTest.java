package com.irunninglog.vertx;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public abstract class AbstractVerticleTest {

    protected IFactory factory;
    protected IMapper mapper;

    @Before
    public final void before(TestContext context) {
        afterBefore(context);
    }

    protected abstract void afterBefore(TestContext context);

    @Rule
    public RunTestOnContext rule = new RunTestOnContext();

}
