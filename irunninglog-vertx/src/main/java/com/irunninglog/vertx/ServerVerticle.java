package com.irunninglog.vertx;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.IAuthenticationService;
import com.irunninglog.vertx.security.SecurityHandler;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public final class ServerVerticle extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(ServerVerticle.class);
    static final int BODY_SIZE_LIMIT = 1024;

    private final int listenPort;
    private final Handler<AsyncResult<HttpServer>> listenHandler;
    private final IFactory factory;
    private final IMapper mapper;
    private final IAuthenticationService authenticationService;

    public ServerVerticle(int listenPort,
                          Handler<AsyncResult<HttpServer>> listenHandler,
                          IFactory factory,
                          IMapper mapper,
                          IAuthenticationService authenticationService) {

        this.listenPort = listenPort;
        this.listenHandler = listenHandler;
        this.factory = factory;
        this.mapper = mapper;
        this.authenticationService = authenticationService;
    }

    @Override
    public void start() throws Exception {
        LOG.info("start:start");

        super.start();

        httpServer();

        LOG.info("start:end");
    }

    private void httpServer() throws IllegalAccessException, InstantiationException, InvocationTargetException, ClassNotFoundException {
        LOG.info("httpServer:start");
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);
        install(router);

        LOG.info("httpServer:listen:before:{}", listenPort);
        server.requestHandler(router::accept).listen(listenPort, listenHandler);
        LOG.info("httpServer:listen:after");

        LOG.info("httpServer:end");
    }

    private void install(Router router) throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        FastClasspathScanner scanner = new FastClasspathScanner("com.irunninglog");
        List<String> classes = scanner.scan().getNamesOfClassesWithAnnotation(EndpointHandler.class);

        router.route().handler(BodyHandler.create().setBodyLimit(1024));

        SecurityHandler securityHandler = new SecurityHandler(authenticationService);
        for (String clazz : classes) {
            AbstractRouteHandler handler = (AbstractRouteHandler) Class.forName(clazz).getConstructors()[0].newInstance(vertx, factory, mapper);

            LOG.info("httpServer:{}:before", handler);
            router.route(HttpMethod.valueOf(handler.endpoint().getMethod().name()), handler.endpoint().getPath()).handler(securityHandler);
            router.route(HttpMethod.valueOf(handler.endpoint().getMethod().name()), handler.endpoint().getPath()).handler(handler);
            LOG.info("httpServer:{}:after", handler);
        }

        router.route("/*").handler(securityHandler);
    }

}
