package org.incode.ixnsubscriber.e2e;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
class AcknowledgingRelayConfig {

    @Bean
    public AcknowledgingRelay acknowledgingRelay() {
        return new AcknowledgingRelay();
    }

}
