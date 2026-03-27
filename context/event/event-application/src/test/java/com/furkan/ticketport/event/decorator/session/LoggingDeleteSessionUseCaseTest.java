package com.furkan.ticketport.event.decorator.session;

import com.furkan.ticketport.event.port.out.LogPort;
import com.furkan.ticketport.event.usecase.session.DeleteSessionService;
import com.furkan.ticketport.event.valueobject.SessionId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoggingDeleteSessionUseCaseTest {

    @Mock
    private DeleteSessionService delegate;

    @Mock
    private LogPort logPort;

    @Test
    void logsAroundDelegate() {
        LoggingDeleteSessionUseCase logging = new LoggingDeleteSessionUseCase(delegate, logPort);
        SessionId id = SessionId.valueOf("dx1");
        logging.execute(id);
        verify(logPort).info(eq("useCase=DeleteSession phase=start sessionId={}"), eq("dx1"));
        verify(delegate).execute(id);
        verify(logPort).info(eq("useCase=DeleteSession phase=completed sessionId={}"), eq("dx1"));
    }
}
