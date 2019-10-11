package org.incode.estcodasubscriber.spi.log.impl;

import org.apache.isis.schema.ixn.v1.InteractionDto;
import org.incode.estcodasubscriber.spi.JaxbService;
import org.incode.estcodasubscriber.spi.Relay;
import org.incode.estcodasubscriber.spi.RelayStatus;
import org.springframework.stereotype.Component;

@Component
public class RelayLog implements Relay {

    private final JaxbService jaxbService;

    public RelayLog(JaxbService jaxbService) {
        this.jaxbService = jaxbService;
    }

    @Override
    public RelayStatus handle(InteractionDto interactionDto) {
        System.out.println(jaxbService.toXml(interactionDto));
        return RelayStatus.OK;
    }

}
