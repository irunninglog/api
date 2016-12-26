package com.irunninglog.vertx.endpoint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;

@Target(value=CONSTRUCTOR)
@Retention(RetentionPolicy.RUNTIME)
public @interface EndpointConstructor {



}
