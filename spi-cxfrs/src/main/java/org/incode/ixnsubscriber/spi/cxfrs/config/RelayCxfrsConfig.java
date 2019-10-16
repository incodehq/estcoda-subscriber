package org.incode.ixnsubscriber.spi.cxfrs.config;

import lombok.Data;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

@ConfigurationProperties("app.relay-cxfrs")
@Data
public class RelayCxfrsConfig {

    /**
     * The base URL of the webapp that hosts the CODA REST APIs.
     */
    private String base = "http://localhost:9090/est2coda/";

    /**
     * The suffix to append to the {@link #getBase()}
     */
    private String uriSuffix = "memberInteractionsQueue";

    /**
     * For testing
     */
    private boolean logOnly = false;
}
