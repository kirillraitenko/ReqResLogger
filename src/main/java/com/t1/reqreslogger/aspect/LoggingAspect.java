package com.t1.reqreslogger.aspect;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Stream;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    //private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* (@org.springframework.web.bind.annotation.RestController *).*(..)) " +
            "&& !@annotation(com.t1.reqreslogger.annotations.LoggingDisabled)")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {


        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();

        long startTime = System.currentTimeMillis();
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("Exception in method: {}", joinPoint.getSignature(), throwable);
            throw throwable;
        }
        long duration = System.currentTimeMillis() - startTime;

        // Log request details
        log.info("____________________");
        log.info("HTTP Method: {}", request.getMethod());
        log.info("Request URL: {}", request.getRequestURL().toString());
        log.info("Request Headers: {}", getRequestHeaders(request));

        // Log response details
        if (result instanceof ResponseEntity) {
            ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
            log.info("Response Status: {}", responseEntity.getStatusCodeValue());
            log.info("Response Headers: {}", responseEntity.getHeaders());
        } else if (response != null) {
            log.info("Response Status: {}", response.getStatus());
        }

        log.info("Processing Time: {} ms", duration);

        return result;
    }

    private String getRequestHeaders(HttpServletRequest request) {
        StringJoiner joiner = new StringJoiner("; ", "[", "]");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            joiner.add(headerName + ": " + headerValue);
        }
        return joiner.toString();
    }
}
