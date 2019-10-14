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
        NONE,
        ZIPKIN
    }

    private final Tracing tracing = new Tracing();
    @Data
    public static class Tracing {
        private TracingType tracingType = TracingType.NONE;
        private final Zipkin zipkin = new Zipkin();
        @Data
        public static class Zipkin {
            private String endpoint = "http://localhost:9411/api/v2/spans";
            private String serviceName = "ixn-subscriber";
            /**
             * 1.0f = capture 100% of all the events
             */
            private float rate = 1.0f;
            /**
             *  Whether to include message bodies in the traces (not recommended for production).
             */
            private boolean includeMessageBodyStreams = true;
        }
    }

}
