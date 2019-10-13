package org.incode.ixnsubscriber.dispatcher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RelayStubConfig {

    @Bean
    public RelayStub acknowledgingRelay() {
        return new RelayStub();
    }

}