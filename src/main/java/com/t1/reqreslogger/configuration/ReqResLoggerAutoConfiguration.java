package com.t1.reqreslogger.configuration;

import com.t1.reqreslogger.aspect.LoggingAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ReqResLoggerProperties.class)
//@ConditionalOnProperty(name = "com.t1.logging.reqreslogger.enabled", havingValue = "true")
public class ReqResLoggerAutoConfiguration {

    @Autowired
    private ReqResLoggerProperties properties;

    @Bean
    //@ConditionalOnMissingBean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
}
