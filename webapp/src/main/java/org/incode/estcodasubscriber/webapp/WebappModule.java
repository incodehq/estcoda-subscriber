package org.incode.estcodasubscriber.webapp;


import org.incode.estcodasubscriber.webapp.config.AppConfig;
import org.incode.estcodasubscriber.webapp.dispatch.Dispatcher;
import org.incode.estcodasubscriber.webapp.dispatch.SubscribingRoute;
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
