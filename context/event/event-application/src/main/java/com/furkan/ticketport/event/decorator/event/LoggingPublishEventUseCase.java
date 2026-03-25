package com.furkan.ticketport.event.decorator.event;

import com.furkan.ticketport.event.command.event.PublishEventCommand;
import com.furkan.ticketport.event.port.in.event.PublishEventUseCase;
import com.furkan.ticketport.event.port.out.LogPort;
import com.furkan.ticketport.event.usecase.event.PublishEventService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class LoggingPublishEventUseCase implements PublishEventUseCase {

    private final PublishEventService delegate;
    private final LogPort logPort;

    public LoggingPublishEventUseCase(PublishEventService delegate, LogPort logPort) {
        this.delegate = delegate;
        this.logPort = logPort;
    }

    @Override
    public void execute(PublishEventCommand cmd) {
        logPort.info("useCase=PublishEvent phase=start eventId={}", cmd.eventId().asString());
        delegate.execute(cmd);
        logPort.info("useCase=PublishEvent phase=completed eventId={}", cmd.eventId().asString());
    }
}
