package com.t1.reqreslogger.aspect;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;
import java.util.StringJoiner;

@Aspect
@Slf4j
public class LoggingAspect {
    @Around(value = """
            execution(* *(..)) && (@within(org.springframework.web.bind.annotation.RestController) \
            || @within(org.springframework.stereotype.Controller)) \
            && !@annotation(com.t1.reqreslogger.annotations.LoggingDisabled)
            && !@within(com.t1.reqreslogger.annotations.LoggingDisabled)
            """)
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();

        Object result = null;
        long startTime = System.currentTimeMillis();
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.info("____________________");
            log.error("Exception in method: {}", joinPoint.getSignature(), throwable);
            throw throwable;
        }
        long duration = System.currentTimeMillis() - startTime;

        // Log request details
        log.info("____________________");
        log.info("Request: \n\tHTTP Method: {};\n\t Request URL: {};\n\t Request Headers: {}\n", request.getMethod(),
                request.getRequestURL().toString(), getRequestHeaders(request));

        if (result instanceof ResponseEntity) {
            ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
            log.info("Response: \n\tStatus: {};\n\t Body : {};\n\t Headers: {}\n", responseEntity.getStatusCodeValue(),
                    responseEntity.getBody(), responseEntity.getHeaders());
        } else if (response != null) {
            log.info("Response Status: {}", response.getStatus());
        }

        log.info("Processing Time: {} ms\n", duration);

        return result;
    }

    private String getRequestHeaders(HttpServletRequest request) {
        StringJoiner joiner = new StringJoiner(")(", "[(", ")]");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            joiner.add(headerName + ": " + headerValue);
        }
        return joiner.toString();
    }
}
