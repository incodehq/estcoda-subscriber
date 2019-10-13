package org.incode.ixnsubscriber.webapp;


import org.incode.ixnsubscriber.webapp.config.AppConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(AppConfig.class)
public class WebappModule {

}
