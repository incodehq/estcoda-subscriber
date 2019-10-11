package org.incode.messagerelay.webapp;

import java.util.UUID;

import org.apache.isis.schema.ixn.v1.ActionInvocationDto;
import org.apache.isis.schema.ixn.v1.InteractionDto;
import org.incode.estcodasubscriber.spi.log.impl.RelayLog;
import org.incode.estcodasubscriber.webapp.EstatioCodaSubscriberApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EstatioCodaSubscriberApplication.class)
public class EstatioCodaSubscriberApplication_IntegTest {

    @Autowired
    RelayLog relayLog;

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
        InteractionDto interactionDto = newInteractionDto();
        relayLog.handle(interactionDto);
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
