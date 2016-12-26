package com.irunninglog.vertx.endpoint;

import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public abstract class AbstractVerticleTest {

    @Rule
    public RunTestOnContext rule = new RunTestOnContext();

}
