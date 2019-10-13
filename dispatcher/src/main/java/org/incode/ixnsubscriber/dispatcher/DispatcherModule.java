package org.incode.ixnsubscriber.dispatcher;


import org.incode.ixnsubscriber.dispatcher.config.AppDispatcherConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({
        Dispatcher.class,
        SubscribingRoute.class
})
@EnableConfigurationProperties(AppDispatcherConfig.class)
public class DispatcherModule {

}
