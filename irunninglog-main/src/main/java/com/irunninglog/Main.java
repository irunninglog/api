package com.irunninglog;

import com.irunninglog.api.profile.IProfileService;
import com.irunninglog.spring.context.ContextConfiguration;
import com.irunninglog.vertx.verticle.ProfileVerticle;
import com.irunninglog.vertx.verticle.ServerVerticle;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ContextConfiguration.class);

        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new ServerVerticle());
        LOG.info("Deployed server verticle");

        IProfileService profileService = applicationContext.getBean(IProfileService.class);
        LOG.info("Created profile service {}", profileService);
        vertx.deployVerticle(new ProfileVerticle(profileService));
        LOG.info("Deployed profile verticle");
    }

}
