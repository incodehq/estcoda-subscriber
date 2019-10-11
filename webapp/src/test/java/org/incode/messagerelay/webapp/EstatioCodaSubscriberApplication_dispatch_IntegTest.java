package org.incode.messagerelay.webapp;

import lombok.Getter;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.isis.schema.ixn.v1.ActionInvocationDto;
import org.apache.isis.schema.ixn.v1.InteractionDto;
import org.incode.estcodasubscriber.spi.JaxbService;
import org.incode.estcodasubscriber.spi.Relay;
import org.incode.estcodasubscriber.spi.RelayModule;
import org.incode.estcodasubscriber.spi.RelayStatus;
import org.incode.estcodasubscriber.webapp.WebappModule;
import org.incode.estcodasubscriber.webapp.config.AppConfig;
import org.incode.estcodasubscriber.webapp.dispatch.SubscribingRoute;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EstatioCodaSubscriberApplication_dispatch_IntegTest.E2EApplication.class)
public class EstatioCodaSubscriberApplication_dispatch_IntegTest {

    @SpringBootApplication
    @Import({
            RelayModule.class,
            WebappModule.class
    })
    @EnableConfigurationProperties({AppConfig.class})
    static class E2EApplication {
    }

    @Autowired
    JaxbService jaxbService;

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    SubscribingRoute subscribingRoute;

    @Autowired
    AcknowledgingRelay relay;

    @Test
    public void happy_case() throws InterruptedException {

        // given
        InteractionDto interactionDto = newInteractionDto();
        String xml = jaxbService.toXml(interactionDto);

        // expect
        relay.toReturn(RelayStatus.OK);

        // when
        jmsTemplate.convertAndSend("memberInteractionsQueue", xml);

        // then
        final InteractionDto returned = relay.receivedAfterMillis(1000);
        final String returnedXml = jaxbService.toXml(returned);

        assertThat(returnedXml).isEqualTo(xml);
    }

    private static InteractionDto newInteractionDto() {
        InteractionDto interactionDto = new InteractionDto();
        interactionDto.setTransactionId(UUID.randomUUID().toString());
        interactionDto.setMajorVersion("1");
        interactionDto.setMinorVersion("4");
        ActionInvocationDto actionInvocationDto = new ActionInvocationDto();
        actionInvocationDto.setSequence(1);
        actionInvocationDto.setLogicalMemberIdentifier("customer.Customer#placeOrder()");
        interactionDto.setExecution(actionInvocationDto);
        return interactionDto;
    }
}

@Configuration
class SenderConfig {

    @Value("${activemq.broker-url}")
    private String brokerUrl;

    @Bean
    public ActiveMQConnectionFactory senderActiveMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory =
                new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(brokerUrl);

        return activeMQConnectionFactory;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        return new CachingConnectionFactory(
                senderActiveMQConnectionFactory());
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(cachingConnectionFactory());
    }

    @Bean
    public Relay stubRelay() {
        return new AcknowledgingRelay();
    }

}

@TestComponent
class AcknowledgingRelay implements Relay {

    private CountDownLatch latch;

    @Getter
    private InteractionDto handleArg;

    private RelayStatus handleReturn = RelayStatus.OK;

    void toReturn(RelayStatus handleReturn) {
        latch = new CountDownLatch(1);
        this.handleReturn = handleReturn;
    }

    InteractionDto receivedAfterMillis(int timeout) throws InterruptedException {
        latch.await(timeout, TimeUnit.MILLISECONDS);
        return handleArg;
    }

    @Override
    public RelayStatus handle(InteractionDto interactionDto) {
        this.handleArg = interactionDto;
        return handleReturn;
    }
}
