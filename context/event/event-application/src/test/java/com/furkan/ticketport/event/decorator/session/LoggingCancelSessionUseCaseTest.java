package com.furkan.ticketport.event.decorator.session;

import com.furkan.ticketport.event.command.session.CancelSessionCommand;
import com.furkan.ticketport.event.port.out.LogPort;
import com.furkan.ticketport.event.usecase.session.CancelSessionService;
import com.furkan.ticketport.event.valueobject.SessionId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoggingCancelSessionUseCaseTest {

    @Mock
    private CancelSessionService delegate;

    @Mock
    private LogPort logPort;

    @Test
    void logsAroundDelegate() {
        LoggingCancelSessionUseCase logging = new LoggingCancelSessionUseCase(delegate, logPort);
        CancelSessionCommand cmd = new CancelSessionCommand(SessionId.valueOf("cx1"));
        logging.execute(cmd);
        verify(logPort).info(eq("useCase=CancelSession phase=start sessionId={}"), eq("cx1"));
        verify(delegate).execute(cmd);
        verify(logPort).info(eq("useCase=CancelSession phase=completed sessionId={}"), eq("cx1"));
    }
}
