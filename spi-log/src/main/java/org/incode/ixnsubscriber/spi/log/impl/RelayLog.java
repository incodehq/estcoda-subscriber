package org.incode.ixnsubscriber.spi.log.impl;

import lombok.Setter;

import org.apache.isis.schema.ixn.v1.InteractionDto;
import org.incode.ixnsubscriber.spi.JaxbService;
import org.incode.ixnsubscriber.spi.Relay;
import org.incode.ixnsubscriber.spi.RelayStatus;
import org.incode.ixnsubscriber.spi.log.config.RelayLogConfig;
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
