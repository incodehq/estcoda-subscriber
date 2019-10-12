package org.incode.ixnsubscriber.spi.log.config;

import lombok.Data;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

@ConfigurationProperties("app.relay-log")
@Data
public class RelayLogConfig {

    private String logPrefix = ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n";

}
