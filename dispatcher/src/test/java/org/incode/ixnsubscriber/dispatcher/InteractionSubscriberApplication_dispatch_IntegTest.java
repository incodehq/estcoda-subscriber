package org.incode.ixnsubscriber.dispatcher;

import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.camel.CamelContext;
import org.apache.isis.schema.ixn.v1.ActionInvocationDto;
import org.apache.isis.schema.ixn.v1.InteractionDto;
import org.incode.ixnsubscriber.dispatcher.config.AppCxfrsConfig;
import org.incode.ixnsubscriber.dispatcher.util.JaxbService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InteractionSubscriberApplication_dispatch_IntegTest.DispatcherRouteTestApp.class)
public class InteractionSubscriberApplication_dispatch_IntegTest {

    @SpringBootApplication
    @Import({
            DispatcherModule.class
    })
    static class DispatcherRouteTestApp {
    }

    @Autowired
    CamelContext camelContext;

    @Autowired
    AppCxfrsConfig appCxfrsConfig;

    ClientAndServer mockServer;

    @Before
    public void startServer() {
        final int port = appCxfrsConfig.getPort();
        mockServer = startClientAndServer(port);
    }

    @After
    public void stopServer() {
         mockServer.stop();
    }

    @Autowired
    JaxbService jaxbService;

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    DispatcherRoute subscribingRoute;

    @Test
    public void happy_case() throws InterruptedException {

        // given
        InteractionDto interactionDto = newInteractionDto();
        String xml = jaxbService.toXml(interactionDto);

        // expect
        final HttpRequest expectedRequest = request().withPath("/est2coda/memberInteractionsQueue")
                .withMethod("POST")
//                .withBody(xml)
                ;
        mockServer.when(expectedRequest).respond(response().withStatusCode(200));

        // when
         jmsTemplate.convertAndSend("memberInteractionsQueue", xml);

        Thread.sleep(1000);

        // then
         mockServer.verify(expectedRequest);
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

