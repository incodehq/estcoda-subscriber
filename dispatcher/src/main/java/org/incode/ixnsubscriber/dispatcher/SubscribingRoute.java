package org.incode.ixnsubscriber.dispatcher;

import org.apache.camel.builder.RouteBuilder;
import org.incode.ixnsubscriber.dispatcher.config.AppDispatcherConfig;
import org.springframework.stereotype.Component;

@Component
public class SubscribingRoute extends RouteBuilder {

    final AppDispatcherConfig appDispatcherConfig;

    public SubscribingRoute(final AppDispatcherConfig appDispatcherConfig) {
        this.appDispatcherConfig = appDispatcherConfig;
    }

    @Override
    public void configure() {
        from("activemq:{{app.dispatcher.queue-name}}")
            .to("direct:receive");
    }

}
