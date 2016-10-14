package com.irunninglog.vertx.routehandler;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

public interface IRouteHandler extends Handler<RoutingContext> {

    HttpMethod method();

    String path();

}
