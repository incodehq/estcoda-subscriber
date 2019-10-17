package org.incode.ixnsubscriber.dispatcher;


import org.incode.ixnsubscriber.dispatcher.config.AppSubscriberConfig;
import org.incode.ixnsubscriber.dispatcher.config.AppCxfrsConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({
        DispatcherRoute.class
})
@EnableConfigurationProperties({AppSubscriberConfig.class, AppCxfrsConfig.class})
public class DispatcherModule {

}
