package com.irunninglog.main;

import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    private Main() {
        // Hide public constructor
    }

    public static void main(String[] args) {
        new RunningLogApplication().start(Vertx.vertx(), stringAsyncResult -> LOG.info("RunningLogApplication successfully started"));
    }

}
