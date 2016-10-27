package com.irunninglog;

import com.irunninglog.api.profile.IProfileService;
import com.irunninglog.api.security.IAuthenticationService;
import com.irunninglog.spring.context.ContextConfiguration;
import com.irunninglog.vertx.verticle.ProfileVerticle;
import com.irunninglog.vertx.verticle.ServerVerticle;
import io.vertx.core.Vertx;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOG.info("main:start");

        LOG.info("main:logging:before");
        logging();
        LOG.info("main:logging:after");

        LOG.info("main:applicationContext:before");
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ContextConfiguration.class);
        LOG.info("main:applicationContext:after");

        LOG.info("main:vertx:before");
        Vertx vertx = Vertx.vertx();
        LOG.info("main:vertx:after");

        LOG.info("main:server:before");
        server(applicationContext, vertx);
        LOG.info("main:server:after");

        LOG.info("main:verticles:before");
        verticles(applicationContext, vertx);
        LOG.info("main:verticles:after");

        LOG.info("main:end");
    }

    private static void verticles(ApplicationContext applicationContext, Vertx vertx) {
        LOG.info("verticles:profile:before");
        IProfileService profileService = applicationContext.getBean(IProfileService.class);
        vertx.deployVerticle(new ProfileVerticle(profileService));
        LOG.info("verticles:profile:before");
    }

    private static void server(ApplicationContext applicationContext, Vertx vertx) {
        Environment environment = applicationContext.getEnvironment();

        int port = environment.getProperty("httpServer.listenPort", Integer.class, 8080);
        LOG.info("server:listenPort:{}", port);

        IAuthenticationService authenticationService = applicationContext.getBean(IAuthenticationService.class);
        LOG.info("server:authenticationService:{}", authenticationService);

        vertx.deployVerticle(new ServerVerticle(port));
    }

    private static void logging() {
        SysOutOverSLF4J.sendSystemOutAndErrToSLF4J();
        System.out.println("logging:System.out");
        System.err.println("logging:System.err");

        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        java.util.logging.Logger julLogger = java.util.logging.Logger.getLogger(Main.class.getName());
        julLogger.info("logging:JUL");

        org.apache.log4j.Logger log4JLogger = org.apache.log4j.Logger.getLogger(Main.class);
        log4JLogger.info("logging:Log4J");

        Log jclLogger = LogFactory.getLog(Main.class);
        jclLogger.info("logging:JCL");
    }

}
