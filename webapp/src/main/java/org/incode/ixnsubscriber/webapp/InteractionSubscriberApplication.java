package org.incode.ixnsubscriber.webapp;

import org.incode.ixnsubscriber.dispatcher.DispatcherModule;
import org.incode.ixnsubscriber.spi.log.RelayLogModule;
import org.incode.ixnsubscriber.spi.rest.RelayRestModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        RelayRestModule.class,
        RelayLogModule.class,
        DispatcherModule.class,
        WebappModule.class
})
public class InteractionSubscriberApplication {

    public static void main(String[] args) {
        SpringApplication.run(InteractionSubscriberApplication.class, args);
    }

}
