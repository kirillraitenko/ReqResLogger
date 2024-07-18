package com.t1.reqreslogger.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "com.t1.logging.reqreslogger")
public class ReqResLoggerProperties {
    private boolean enabled;
    // getters and setters
}
