package org.incode.estcodasubscriber.spi;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        JaxbService.class
})
public class RelayModule {

}
