package com.furkan.ticketport.event.decorator.session;

import com.furkan.ticketport.event.port.in.session.DeleteSessionUseCase;
import com.furkan.ticketport.event.port.out.LogPort;
import com.furkan.ticketport.event.usecase.session.DeleteSessionService;
import com.furkan.ticketport.event.valueobject.SessionId;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class LoggingDeleteSessionUseCase implements DeleteSessionUseCase {

    private final DeleteSessionService delegate;
    private final LogPort logPort;

    public LoggingDeleteSessionUseCase(DeleteSessionService delegate, LogPort logPort) {
        this.delegate = delegate;
        this.logPort = logPort;
    }

    @Override
    public void execute(SessionId sessionId) {
        logPort.info("useCase=DeleteSession phase=start sessionId={}", sessionId.asString());
        delegate.execute(sessionId);
        logPort.info("useCase=DeleteSession phase=completed sessionId={}", sessionId.asString());
    }
}
