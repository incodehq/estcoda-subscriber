package org.incode.ixnsubscriber.spi.cxfrs.impl;

import java.net.InetSocketAddress;
import java.util.UUID;

import org.apache.camel.CamelContext;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.component.properties.springboot.PropertiesComponentConfiguration;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.apache.camel.test.spring.CamelSpringRunner;
import org.apache.isis.schema.ixn.v1.ActionInvocationDto;
import org.apache.isis.schema.ixn.v1.InteractionDto;
import org.incode.ixnsubscriber.spi.JaxbService;
import org.incode.ixnsubscriber.spi.RelayStatus;
import org.incode.ixnsubscriber.spi.cxfrs.RelayCxfrsModule;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.*;
import static org.mockserver.model.HttpResponse.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RelayCxfrs_IntegTest.RelayRestRouteTestApp.class)
public class RelayCxfrs_IntegTest {

    @SpringBootApplication
    @Import({RelayCxfrsModule.class})
    static class RelayRestRouteTestApp { }

    static ClientAndServer mockServer;

    @BeforeClass
    public static void startServer() {
        mockServer = startClientAndServer(randomInRange(1080, 5000));
    }

    @AfterClass
    public static void stopServer() {
        mockServer.stop();
    }

    @Autowired
    RelayCxfrs relayCxfrsRest;

    @Autowired
    CamelContext camelContext;

    @Autowired
    JaxbService jaxbService;

    @Before
    public void setUp() throws Exception {
        InetSocketAddress inetSocketAddress = mockServer.remoteAddress();
        relayCxfrsRest.setBase(String.format("http://%s:%d/%s", inetSocketAddress.getHostName(), mockServer.getPort(), mockServer.contextPath()));
        relayCxfrsRest.init();
    }

    @Test
    public void happy_case() {

        // given
        InteractionDto interactionDto = newInteractionDto();
        String xml = jaxbService.toXml(interactionDto);

        // expect
        final HttpRequest expectedRequest = request().withPath("/memberInteractionsQueue")
                .withMethod("POST")
                .withBody(xml);
        mockServer.when(expectedRequest).respond(response().withStatusCode(200));

        // when
        RelayStatus relayStatus = relayCxfrsRest.handle(interactionDto);

        // then
        mockServer.verify(expectedRequest);
        assertThat(relayStatus).isEqualTo(RelayStatus.OK);
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

    private static int randomInRange(int from, int range) {
        return (int) (from + (Math.random() * range));
    }


}
