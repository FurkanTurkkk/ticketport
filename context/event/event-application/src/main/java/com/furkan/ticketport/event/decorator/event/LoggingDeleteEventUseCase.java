package com.furkan.ticketport.event.decorator.event;

import com.furkan.ticketport.event.port.in.event.DeleteEventUseCase;
import com.furkan.ticketport.event.port.out.LogPort;
import com.furkan.ticketport.event.usecase.event.DeleteEventService;
import com.furkan.ticketport.event.valueobject.EventId;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class LoggingDeleteEventUseCase implements DeleteEventUseCase {

    private final DeleteEventService delegate;
    private final LogPort logPort;

    public LoggingDeleteEventUseCase(DeleteEventService delegate, LogPort logPort) {
        this.delegate = delegate;
        this.logPort = logPort;
    }

    @Override
    public void execute(EventId eventId) {
        logPort.info("useCase=DeleteEvent phase=start eventId={}", eventId.asString());
        delegate.execute(eventId);
        logPort.info("useCase=DeleteEvent phase=completed eventId={}", eventId.asString());
    }
}
