package com.furkan.ticketport.event.decorator.event;

import com.furkan.ticketport.event.command.event.UnpublishEventCommand;
import com.furkan.ticketport.event.port.in.event.UnpublishEventUseCase;
import com.furkan.ticketport.event.port.out.LogPort;
import com.furkan.ticketport.event.usecase.event.UnpublishEventService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class LoggingUnpublishEventUseCase implements UnpublishEventUseCase {

    private final UnpublishEventService delegate;
    private final LogPort logPort;

    public LoggingUnpublishEventUseCase(UnpublishEventService delegate, LogPort logPort) {
        this.delegate = delegate;
        this.logPort = logPort;
    }

    @Override
    public void execute(UnpublishEventCommand cmd) {
        logPort.info("useCase=UnpublishEvent phase=start eventId={}", cmd.eventId().asString());
        delegate.execute(cmd);
        logPort.info("useCase=UnpublishEvent phase=completed eventId={}", cmd.eventId().asString());
    }
}
