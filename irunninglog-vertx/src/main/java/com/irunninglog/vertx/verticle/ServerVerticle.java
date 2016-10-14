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

    @Override
    public void start() throws Exception {
        super.start();

        httpServer();

        LOG.info("Server verticle started");
    }

    private void httpServer() {
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        IRouteHandler getProfileHandler = new GetProfileHandler(vertx);
        router.route(getProfileHandler.method(), getProfileHandler.path()).handler(getProfileHandler);

        server.requestHandler(router::accept).listen(8080);
    }

}
