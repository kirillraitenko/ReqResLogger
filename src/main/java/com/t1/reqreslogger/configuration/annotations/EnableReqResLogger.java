package com.t1.reqreslogger.configuration.annotations;

import com.t1.reqreslogger.configuration.ReqResLoggerAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Install this annotation in your project to enable logging
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(ReqResLoggerAutoConfiguration.class)
public @interface EnableReqResLogger {
}
