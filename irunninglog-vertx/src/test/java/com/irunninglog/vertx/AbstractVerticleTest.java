package com.irunninglog.vertx;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.vertx.mock.MockFactory;
import com.irunninglog.vertx.mock.MockMapper;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public abstract class AbstractVerticleTest {

    protected final IFactory factory = new MockFactory();
    protected final IMapper mapper = new MockMapper();

    @Rule
    public RunTestOnContext rule = new RunTestOnContext();

}
