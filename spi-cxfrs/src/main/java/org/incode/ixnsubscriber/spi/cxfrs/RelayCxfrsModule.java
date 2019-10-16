package org.incode.ixnsubscriber.spi.cxfrs;


import org.incode.ixnsubscriber.spi.RelayModule;
import org.incode.ixnsubscriber.spi.cxfrs.config.RelayCxfrsConfig;
import org.incode.ixnsubscriber.spi.cxfrs.impl.RelayCxfrs;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({
        RelayCxfrs.class,
        RelayModule.class
})
@EnableConfigurationProperties(RelayCxfrsConfig.class)
public class RelayCxfrsModule {

}
