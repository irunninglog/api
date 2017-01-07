package com.irunninglog.vertx.route;

import com.irunninglog.security.AccessControl;
import com.irunninglog.vertx.Address;
import io.vertx.core.http.HttpMethod;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Target(value=TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RouteHandler {

    HttpMethod method();

    String path();

    Address address();

    AccessControl access() default AccessControl.DenyAll;

}
