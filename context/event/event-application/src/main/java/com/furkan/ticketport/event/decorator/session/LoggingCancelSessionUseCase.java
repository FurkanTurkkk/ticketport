package com.furkan.ticketport.event.decorator.session;

import com.furkan.ticketport.event.command.session.CancelSessionCommand;
import com.furkan.ticketport.event.port.in.session.CancelSessionUseCase;
import com.furkan.ticketport.event.port.out.LogPort;
import com.furkan.ticketport.event.usecase.session.CancelSessionService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class LoggingCancelSessionUseCase implements CancelSessionUseCase {

    private final CancelSessionService delegate;
    private final LogPort logPort;

    public LoggingCancelSessionUseCase(CancelSessionService delegate, LogPort logPort) {
        this.delegate = delegate;
        this.logPort = logPort;
    }

    @Override
    public void execute(CancelSessionCommand cmd) {
        logPort.info("useCase=CancelSession phase=start sessionId={}", cmd.sessionId().asString());
        delegate.execute(cmd);
        logPort.info("useCase=CancelSession phase=completed sessionId={}", cmd.sessionId().asString());
    }
}
