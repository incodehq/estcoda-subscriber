package org.incode.ixnsubscriber.dispatcher;

import org.apache.camel.builder.RouteBuilder;
import org.incode.ixnsubscriber.dispatcher.config.AppSubscriberConfig;
import org.springframework.stereotype.Component;

@Component
public class DispatcherRoute extends RouteBuilder {

    @Override
    public void configure() {
        from("activemq:{{app.subscriber.queue-name}}")
                .setHeader("CamelHttpMethod", () -> "POST")
         .to("cxfrs:" +
                 "{{app.cxfrs.scheme}}://" +
                 "{{app.cxfrs.host}}:" +
                 "{{app.cxfrs.port}}/" +
                 "{{app.cxfrs.context-path}}/" +
                 "{{app.cxfrs.uri-suffix}}" +
                 "?synchronous=true");
    }

}
