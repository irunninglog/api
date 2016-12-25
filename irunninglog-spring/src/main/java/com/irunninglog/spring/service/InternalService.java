package com.irunninglog.spring.service;

import org.springframework.stereotype.Service;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Target(value=TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Service
public @interface InternalService {

}
