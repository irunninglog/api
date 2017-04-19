package com.irunninglog.vertx;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.IRequest;
import com.irunninglog.api.IResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Target(value=TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EndpointVerticle {

    Endpoint endpoint();

    Class<? extends IRequest> request();

    Class<? extends IResponse> response();

}
