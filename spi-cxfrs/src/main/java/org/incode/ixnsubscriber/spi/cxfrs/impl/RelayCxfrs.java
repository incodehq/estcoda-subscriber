package org.incode.ixnsubscriber.spi.cxfrs.impl;

import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.time.Duration;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.UriBuilder;

import org.apache.camel.*;
import org.apache.isis.schema.ixn.v1.InteractionDto;
import org.incode.ixnsubscriber.spi.Relay;
import org.incode.ixnsubscriber.spi.RelayStatus;
import org.incode.ixnsubscriber.spi.JaxbService;
import org.incode.ixnsubscriber.spi.cxfrs.config.RelayCxfrsConfig;
import org.springframework.stereotype.Component;

@Component
public class RelayCxfrs implements Relay {

    private final JaxbService jaxbService;

    private final CamelContext camelContext;
    @Setter
    String base;

    @Setter
    String uriSuffix;

    @Setter
    Duration connectionTimeout;

    @Setter
    Duration receiveTimeout;

    @Setter
    boolean logOnly;

    URI uri;
    private ProducerTemplate producerTemplate;

    public RelayCxfrs(JaxbService jaxbService, CamelContext camelContext, RelayCxfrsConfig config) {

        this.jaxbService = jaxbService;
        this.camelContext = camelContext;

        this.base = config.getBase();
        this.uriSuffix = config.getUriSuffix();
        this.logOnly = config.isLogOnly();

    }

    @PostConstruct
    public void init() {
        this.uri = UriBuilder.fromUri(this.base + this.uriSuffix).build();

        this.producerTemplate = camelContext.createProducerTemplate();
        this.producerTemplate.setDefaultEndpointUri("cxfrs:" + this.uri.toString());
    }

    @Override
    public RelayStatus handle(InteractionDto interactionDto) {
        final String xml = jaxbService.toXml(interactionDto);
        try {
            producerTemplate.sendBody(xml);
            return RelayStatus.OK;
        } catch(Exception ex) {
            return RelayStatus.FAILED;
        }
    }

}
