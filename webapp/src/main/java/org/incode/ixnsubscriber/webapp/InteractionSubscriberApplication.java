package org.incode.ixnsubscriber.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        WebappModule.class
})
public class InteractionSubscriberApplication {

    public static void main(String[] args) {
        SpringApplication.run(InteractionSubscriberApplication.class, args);
    }

}
