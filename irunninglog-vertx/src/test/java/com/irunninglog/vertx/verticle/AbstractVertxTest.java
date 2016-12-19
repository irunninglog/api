package com.irunninglog.vertx.verticle;

import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public abstract class AbstractVertxTest {

    @Rule
    public RunTestOnContext rule = new RunTestOnContext();

}
