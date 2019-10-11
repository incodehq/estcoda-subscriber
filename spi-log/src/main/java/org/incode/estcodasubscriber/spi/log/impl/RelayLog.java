package org.incode.estcodasubscriber.spi.log.impl;

import lombok.Setter;

import org.apache.isis.schema.ixn.v1.InteractionDto;
import org.incode.estcodasubscriber.spi.JaxbService;
import org.incode.estcodasubscriber.spi.Relay;
import org.incode.estcodasubscriber.spi.RelayStatus;
import org.incode.estcodasubscriber.spi.log.config.RelayLogConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RelayLog implements Relay {

    private final JaxbService jaxbService;

    @Setter
    private String logPrefix;

    public RelayLog(JaxbService jaxbService, RelayLogConfig config) {
        this.jaxbService = jaxbService;
        this.logPrefix = config.getLogPrefix();
    }

    @Override
    public RelayStatus handle(InteractionDto interactionDto) {
        System.out.println(logPrefix + jaxbService.toXml(interactionDto));
        return RelayStatus.OK;
    }

}
