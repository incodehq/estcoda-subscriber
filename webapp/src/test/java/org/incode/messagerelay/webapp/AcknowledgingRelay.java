package org.incode.messagerelay.webapp;

import lombok.Getter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.isis.schema.ixn.v1.InteractionDto;
import org.incode.ixnsubscriber.spi.Relay;
import org.incode.ixnsubscriber.spi.RelayStatus;

class AcknowledgingRelay implements Relay {

    private CountDownLatch latch;

    @Getter
    private InteractionDto handleArg;

    private RelayStatus handleReturn = RelayStatus.OK;

    void toReturn(RelayStatus handleReturn) {
        latch = new CountDownLatch(1);
        this.handleReturn = handleReturn;
    }

    InteractionDto awaitMillis(int timeout) throws InterruptedException {
        latch.await(timeout, TimeUnit.MILLISECONDS);
        return handleArg;
    }

    @Override
    public RelayStatus handle(InteractionDto interactionDto) {
        this.handleArg = interactionDto;
        return handleReturn;
    }
}
