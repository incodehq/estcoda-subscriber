package org.incode.estcodasubscriber.spi.log;


import org.incode.estcodasubscriber.spi.RelayModule;
import org.incode.estcodasubscriber.spi.log.impl.RelayLog;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        RelayLog.class,
        RelayModule.class
})
public class RelayLogModule {

}
