package org.incode.estcodasubscriber.webapp;

import org.incode.estcodasubscriber.spi.log.RelayLogModule;
//import org.incode.estcodasubscriber.spi.rest.RelayRestModule;
import org.incode.estcodasubscriber.webapp.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        //RelayRestModule.class,
        RelayLogModule.class
})
@EnableConfigurationProperties(AppConfig.class)
public class EstatioCodaSubscriberApplication {

    public static void main(String[] args) {
        SpringApplication.run(EstatioCodaSubscriberApplication.class, args);
    }

}
