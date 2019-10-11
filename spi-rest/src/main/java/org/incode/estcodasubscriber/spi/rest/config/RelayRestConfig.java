package org.incode.estcodasubscriber.spi.rest.config;

import lombok.Data;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

@ConfigurationProperties("app.relay-rest")
@Data
public class RelayRestConfig {

    /**
     * The base URL of the webapp that hosts the CODA REST APIs.
     */
    private String base = "http://localhost:9090/est2coda/";
    /**
     * The user name to invoke the {@link #getBase() REST API}
     */
    private String username = "alex";
    /**
     * The corresponding password for the {@link #getUsername() user}.
     */
    private String password = "pass";

    /**
     * The suffix to append to the {@link #getBase()}
     */
    private String uriSuffix = "memberInteractionsQueue";

    /**
     * How long before giving up attempting to initially connect
     */
    @DurationUnit(ChronoUnit.SECONDS)
    private Duration connectionTimeout = Duration.ofSeconds(30);

    /**
     * How long before giving up when waiting for a response
     */
    @DurationUnit(ChronoUnit.SECONDS)
    private Duration receiveTimeout = Duration.ofSeconds(60);

}
