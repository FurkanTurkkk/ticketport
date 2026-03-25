package com.furkan.ticketport.event.decorator.event;

import com.furkan.ticketport.event.command.event.CreateEventCommand;
import com.furkan.ticketport.event.port.in.event.CreateEventUseCase;
import com.furkan.ticketport.event.port.out.LogPort;
import com.furkan.ticketport.event.usecase.event.CreateEventService;
import com.furkan.ticketport.event.valueobject.EventId;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class LoggingCreateEventUseCase implements CreateEventUseCase {

    private final CreateEventService delegate;
    private final LogPort logPort;

    public LoggingCreateEventUseCase(CreateEventService delegate, LogPort logPort) {
        this.delegate = delegate;
        this.logPort = logPort;
    }

    @Override
    public EventId execute(CreateEventCommand cmd) {
        logPort.info(
                "useCase=CreateEvent phase=start categoryId={} titleLength={}",
                cmd.categoryId().asString(),
                cmd.title().asString().length());
        EventId result = delegate.execute(cmd);
        logPort.info("useCase=CreateEvent phase=completed eventId={}", result.asString());
        return result;
    }
}
