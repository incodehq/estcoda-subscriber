package org.incode.ixnsubscriber.webapp.dispatch;


import java.io.StringReader;
import java.util.List;
import java.util.Optional;

import org.apache.camel.Consume;
import org.apache.isis.schema.ixn.v1.InteractionDto;
import org.incode.ixnsubscriber.spi.Relay;
import org.incode.ixnsubscriber.spi.RelayStatus;
import org.incode.ixnsubscriber.spi.JaxbService;
import org.springframework.stereotype.Component;

@Component
public class Dispatcher {

    private final List<Relay> relays;
    private final JaxbService jaxbService;

    public Dispatcher(
            final List<Relay> relays,
            final JaxbService jaxbService) {
        this.relays = relays;
        this.jaxbService = jaxbService;
    }

    @Consume(uri = "direct:receive")
    public void receive(final String xml) {
        final InteractionDto dto = jaxbService.fromXml(new StringReader(xml), InteractionDto.class);
        dispatchToAllThenDelete(dto);
    }


    /**
     * Pass interaction onto all downstream relays.
     *
     * <p>
     *   Only if all succeed do we remove the interaction from upstream.
     * </p>
     */
    private void dispatchToAllThenDelete(InteractionDto interactionDto) {
        final Optional<RelayStatus> anyFailed =
                relays.stream()
                        .map(relay -> relay.handle(interactionDto))
                        .filter(x -> x == RelayStatus.FAILED)
                        .findAny();
        if (anyFailed.isPresent()) {
            throw new RuntimeException("Failed to dispatch");
        }
    }

}
