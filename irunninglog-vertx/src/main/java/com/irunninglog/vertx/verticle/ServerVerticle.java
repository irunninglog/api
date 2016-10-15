package com.irunninglog.vertx.verticle;

import com.irunninglog.vertx.routehandler.GetProfileHandler;
import com.irunninglog.vertx.routehandler.IRouteHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private void httpServer() {
        LOG.info("httpServer:start");
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        LOG.info("httpServer:getProfileHandler:before");
        IRouteHandler getProfileHandler = new GetProfileHandler(vertx);
        router.route(getProfileHandler.method(), getProfileHandler.path()).handler(getProfileHandler);
        LOG.info("httpServer:getProfileHandler:after");

        LOG.info("httpServer:listen:before:{}", listenPort);
        server.requestHandler(router::accept).listen(listenPort);
        LOG.info("httpServer:listen:after");

        LOG.info("httpServer:end");
    }

}
