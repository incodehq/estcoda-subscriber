package org.incode.estcodasubscriber.spi.log;


import org.incode.estcodasubscriber.spi.RelayModule;
import org.incode.estcodasubscriber.spi.log.config.RelayLogConfig;
import org.incode.estcodasubscriber.spi.log.impl.RelayLog;
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
