package com.irunninglog.vertx;

import com.irunninglog.api.Endpoint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Target(value=TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EndpointHandler {

    Endpoint endpoint();

}
