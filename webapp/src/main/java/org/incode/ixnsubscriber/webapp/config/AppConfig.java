package org.incode.ixnsubscriber.webapp.config;

import lombok.Data;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

@ConfigurationProperties("app")
@Data
public class AppConfig {

    private String queueName = "memberInteractionsQueue";


}
