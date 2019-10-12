package org.incode.ixnsubscriber.spi.rest;


import org.incode.ixnsubscriber.spi.RelayModule;
import org.incode.ixnsubscriber.spi.rest.config.RelayRestConfig;
import org.incode.ixnsubscriber.spi.rest.impl.RelayRest;
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
