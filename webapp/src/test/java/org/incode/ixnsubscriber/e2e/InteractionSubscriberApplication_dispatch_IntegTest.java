package org.incode.ixnsubscriber.e2e;

import java.util.UUID;

import org.apache.isis.schema.ixn.v1.ActionInvocationDto;
import org.apache.isis.schema.ixn.v1.InteractionDto;
import org.incode.ixnsubscriber.spi.JaxbService;
import org.incode.ixnsubscriber.spi.RelayModule;
import org.incode.ixnsubscriber.spi.RelayStatus;
import org.incode.ixnsubscriber.webapp.WebappModule;
import org.incode.ixnsubscriber.webapp.config.AppConfig;
import org.incode.ixnsubscriber.webapp.dispatch.SubscribingRoute;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InteractionSubscriberApplication_dispatch_IntegTest.E2EApplication.class)
public class InteractionSubscriberApplication_dispatch_IntegTest {

    @SpringBootApplication
    @Import({
            RelayModule.class,
            WebappModule.class
    })
    static class E2EApplication {
    }

    @Autowired
    JaxbService jaxbService;

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    SubscribingRoute subscribingRoute;

    @Autowired
    RelayStub relay;

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
        final InteractionDto returned = relay.awaitMillis(1000);
        final String returnedXml = jaxbService.toXml(returned);

        assertThat(returnedXml).isEqualTo(xml);
    }

    @Ignore // this works, but it's not clear to me where we should handle an exception, or how this will work exactly in "real-life"
    @Test
    public void sad_case() throws InterruptedException {

        // given
        InteractionDto interactionDto = newInteractionDto();
        String xml = jaxbService.toXml(interactionDto);

        // expect
        relay.toReturn(RelayStatus.FAILED);

        // when
        jmsTemplate.convertAndSend("memberInteractionsQueue", xml);

        // then
        relay.awaitMillis(1000);

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

