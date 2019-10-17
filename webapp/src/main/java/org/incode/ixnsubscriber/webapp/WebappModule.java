package org.incode.ixnsubscriber.webapp;


import org.incode.ixnsubscriber.dispatcher.DispatcherModule;
import org.incode.ixnsubscriber.webapp.config.AppConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({
        DispatcherModule.class
})
@EnableConfigurationProperties(AppConfig.class)
public class WebappModule {

}
