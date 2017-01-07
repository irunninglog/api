package com.irunninglog.vertx.http;

import com.irunninglog.vertx.route.AbstractRouteHandler;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public final class ServerVerticle extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(ServerVerticle.class);

    private final int listenPort;

    public ServerVerticle(int listenPort) {
        this.listenPort = listenPort;
    }

    @Override
    public void start() throws Exception {
        LOG.info("start:start");

        super.start();

        httpServer();

        LOG.info("start:end");
    }

    private void httpServer() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        LOG.info("httpServer:start");
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);
        install(router);

        LOG.info("httpServer:listen:before:{}", listenPort);
        server.requestHandler(router::accept).listen(listenPort);
        LOG.info("httpServer:listen:after");

        LOG.info("httpServer:end");
    }

    private void install(Router router) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Reflections reflections = new Reflections("com.irunninglog");

        Set<Class<?>> set = reflections.getTypesAnnotatedWith(RouteHandler.class);

        for (Class<?> clazz : set) {
            AbstractRouteHandler<?, ?> handler = (AbstractRouteHandler) clazz.getConstructors()[0].newInstance(vertx);

            LOG.info("httpServer:{}:before", handler);
            router.route(HttpMethod.valueOf(handler.endpoint().getMethod().name()), handler.endpoint().getPath()).handler(handler);
            LOG.info("httpServer:{}:after", handler);
        }
    }

}
