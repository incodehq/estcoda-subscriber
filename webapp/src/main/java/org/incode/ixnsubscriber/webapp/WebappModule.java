package org.incode.ixnsubscriber.webapp;


import org.incode.ixnsubscriber.webapp.config.AppConfig;
import org.incode.ixnsubscriber.webapp.dispatch.Dispatcher;
import org.incode.ixnsubscriber.webapp.dispatch.SubscribingRoute;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({
        Dispatcher.class,
        SubscribingRoute.class
})
@EnableConfigurationProperties(AppConfig.class)
public class WebappModule {

}
