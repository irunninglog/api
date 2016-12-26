package com.irunninglog.main;

import com.irunninglog.spring.context.ContextConfiguration;
import com.irunninglog.vertx.endpoint.EndpointVerticle;
import com.irunninglog.vertx.endpoint.EndpointConstructor;
import com.irunninglog.vertx.http.ServerVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

final class RunningLogApplication {

    private static final Logger LOG = LoggerFactory.getLogger(RunningLogApplication.class);

    void start(Vertx vertx, Handler<AsyncResult<String>> asyncResultHandler) {
        LOG.info("start:start");

        LOG.info("start:logging:before");
        logging();
        LOG.info("start:logging:after");

        LOG.info("start:applicationContext:before");
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ContextConfiguration.class);
        LOG.info("start:applicationContext:after");

        LOG.info("start:server:before");
        server(applicationContext, vertx);
        LOG.info("start:server:after");

        LOG.info("start:verticles:before");
        verticles(applicationContext, vertx, asyncResultHandler);
        LOG.info("start:verticles:after");

        LOG.info("start:end");
    }

    private void verticles(ApplicationContext applicationContext, Vertx vertx, Handler<AsyncResult<String>> asyncResultHandler) {
        List<AbstractVerticle> list = new ArrayList<>();

        Reflections reflections = new Reflections("com.irunninglog");
        Set<Class<?>> set = reflections.getTypesAnnotatedWith(EndpointVerticle.class);
        for (Class<?> clazz : set) {
            LOG.info("Configuring endpoint verticle {}", clazz);
            Reflections reflections1 = new Reflections(clazz.getName(), new MethodAnnotationsScanner(), new TypeAnnotationsScanner(), new SubTypesScanner());
            Set<Constructor> constructors = reflections1.getConstructorsAnnotatedWith(EndpointConstructor.class);
            if (constructors == null || constructors.isEmpty()) {
                LOG.error("Not suitable endpoint constructors found!");
            } else if (constructors.size() > 1) {
                LOG.error("Expected exactly one constructor but found {}", constructors.size());
            } else {
                Constructor constructor = constructors.iterator().next();
                Parameter[] parameters = constructor.getParameters();
                Object [] args = new Object[parameters.length];
                for (int i = 0; i < parameters.length; i++) {
                    args[i] = applicationContext.getBean(parameters[i].getType());
                }

                try {
                    list.add((AbstractVerticle) constructor.newInstance(args));
                } catch (Exception ex) {
                    LOG.error("Unable to create verticle", ex);
                }
            }
        }

        deployVerticles(list.iterator(), asyncResultHandler, vertx);
    }

    private void deployVerticles(Iterator<AbstractVerticle> iterator, Handler<AsyncResult<String>> asyncResultHandler, Vertx vertx) {
        if (iterator.hasNext()) {
            AbstractVerticle verticle = iterator.next();
            if (iterator.hasNext()) {
                vertx.deployVerticle(verticle, stringAsyncResult -> deployVerticles(iterator, asyncResultHandler, vertx));
            } else {
                vertx.deployVerticle(verticle, asyncResultHandler);
            }
        }
    }

    private void server(ApplicationContext applicationContext, Vertx vertx) {
        Environment environment = applicationContext.getEnvironment();

        int port = environment.getProperty("httpServer.listenPort", Integer.class, 8080);
        LOG.info("server:listenPort:{}", port);

        vertx.deployVerticle(new ServerVerticle(port));
    }

    private void logging() {
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
