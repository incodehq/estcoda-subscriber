package org.incode.estcodasubscriber.spi;

import org.apache.isis.schema.ixn.v1.InteractionDto;

public interface Relay {
    RelayStatus handle(InteractionDto interactionDto);
}
