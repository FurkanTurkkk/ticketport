package com.furkan.ticketport.event.decorator.session;

import com.furkan.ticketport.event.command.session.CreateSessionCommand;
import com.furkan.ticketport.event.port.in.session.CreateSessionUseCase;
import com.furkan.ticketport.event.port.out.LogPort;
import com.furkan.ticketport.event.usecase.session.CreateSessionService;
import com.furkan.ticketport.event.valueobject.SessionId;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class LoggingCreateSessionUseCase implements CreateSessionUseCase {

    private final CreateSessionService delegate;
    private final LogPort logPort;

    public LoggingCreateSessionUseCase(CreateSessionService delegate, LogPort logPort) {
        this.delegate = delegate;
        this.logPort = logPort;
    }

    @Override
    public SessionId execute(CreateSessionCommand cmd) {
        logPort.info("useCase=CreateSession phase=start eventId={}", cmd.eventId().asString());
        SessionId result = delegate.execute(cmd);
        logPort.info("useCase=CreateSession phase=completed sessionId={}", result.asString());
        return result;
    }
}
