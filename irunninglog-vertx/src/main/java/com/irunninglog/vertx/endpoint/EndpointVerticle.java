package com.irunninglog.vertx.endpoint;

import com.irunninglog.service.Endpoint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Target(value=TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EndpointVerticle {

    Endpoint endpoint();

    Class<?> [] constructorArgs() default {};

}
