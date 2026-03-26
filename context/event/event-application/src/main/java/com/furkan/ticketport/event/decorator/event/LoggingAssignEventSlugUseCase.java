package com.furkan.ticketport.event.decorator.event;

import com.furkan.ticketport.event.command.event.AssignEventSlugCommand;
import com.furkan.ticketport.event.port.in.event.AssignEventSlugUseCase;
import com.furkan.ticketport.event.port.out.LogPort;
import com.furkan.ticketport.event.usecase.event.AssignEventSlugService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class LoggingAssignEventSlugUseCase implements AssignEventSlugUseCase {

    private final AssignEventSlugService delegate;
    private final LogPort logPort;

    public LoggingAssignEventSlugUseCase(AssignEventSlugService delegate, LogPort logPort) {
        this.delegate = delegate;
        this.logPort = logPort;
    }

    @Override
    public void execute(AssignEventSlugCommand cmd) {
        logPort.info(
                "useCase=AssignEventSlug phase=start eventId={} slug={}",
                cmd.eventId().asString(),
                cmd.slug().asString());
        delegate.execute(cmd);
        logPort.info("useCase=AssignEventSlug phase=completed eventId={}", cmd.eventId().asString());
    }
}
