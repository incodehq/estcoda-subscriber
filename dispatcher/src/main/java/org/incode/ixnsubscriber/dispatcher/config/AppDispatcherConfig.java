package org.incode.ixnsubscriber.dispatcher.config;

import lombok.Data;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

@ConfigurationProperties("app.dispatcher")
@Data
public class AppDispatcherConfig {

    private String queueName = "memberInteractionsQueue";


}
