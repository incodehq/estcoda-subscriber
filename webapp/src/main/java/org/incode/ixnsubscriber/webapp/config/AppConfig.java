package org.incode.ixnsubscriber.webapp.config;

import lombok.Data;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

@ConfigurationProperties("app")
@Data
public class AppConfig {

    public enum TracingType {
        NONE
    }

    private final Tracing tracing = new Tracing();
    @Data
    public static class Tracing {
        private TracingType tracingType = TracingType.NONE;
    }

}
