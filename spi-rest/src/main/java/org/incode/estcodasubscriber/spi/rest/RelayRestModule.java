package org.incode.estcodasubscriber.spi.rest;


import org.incode.estcodasubscriber.spi.RelayModule;
import org.incode.estcodasubscriber.spi.rest.config.RelayRestConfig;
import org.incode.estcodasubscriber.spi.rest.impl.RelayRest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({
        RelayRest.class,
        RelayModule.class
})
@EnableConfigurationProperties(RelayRestConfig.class)
public class RelayRestModule {

}
