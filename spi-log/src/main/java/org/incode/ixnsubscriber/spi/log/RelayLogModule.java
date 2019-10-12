package org.incode.ixnsubscriber.spi.log;


import org.incode.ixnsubscriber.spi.RelayModule;
import org.incode.ixnsubscriber.spi.log.config.RelayLogConfig;
import org.incode.ixnsubscriber.spi.log.impl.RelayLog;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        RelayLog.class,
        RelayModule.class
})
@EnableConfigurationProperties(RelayLogConfig.class)
public class RelayLogModule {

}
