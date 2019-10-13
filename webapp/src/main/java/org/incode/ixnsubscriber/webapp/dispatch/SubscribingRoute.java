package org.incode.ixnsubscriber.webapp.dispatch;

import org.apache.camel.builder.RouteBuilder;
import org.incode.ixnsubscriber.webapp.config.AppConfig;
import org.springframework.stereotype.Component;

@Component
public class SubscribingRoute extends RouteBuilder {

    final AppConfig appConfig;

    public SubscribingRoute(final AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    public void configure() {
        from("activemq:{{app.queue-name}}")
            .to("direct:receive");
    }

}
