package org.incode.estcodasubscriber.spi.rest.impl;

import org.apache.isis.schema.ixn.v1.InteractionDto;
import org.incode.estcodasubscriber.spi.Relay;
import org.incode.estcodasubscriber.spi.RelayStatus;
import org.incode.estcodasubscriber.spi.JaxbService;
import org.springframework.stereotype.Component;

@Component
public class RelayRest implements Relay {

    private final JaxbService jaxbService;

    public RelayRest(JaxbService jaxbService) {
        this.jaxbService = jaxbService;
    }

    @Override
    public RelayStatus handle(InteractionDto interactionDto) {
        throw new RuntimeException("not yet implemented");
    }

}
