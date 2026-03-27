package com.furkan.ticketport.event.decorator.session;

import com.furkan.ticketport.event.command.session.CreateSessionCommand;
import com.furkan.ticketport.event.port.out.LogPort;
import com.furkan.ticketport.event.usecase.session.CreateSessionService;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.SessionId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoggingCreateSessionUseCaseTest {

    @Mock
    private CreateSessionService delegate;

    @Mock
    private LogPort logPort;

    @Test
    void logsAroundDelegate() {
        LoggingCreateSessionUseCase logging = new LoggingCreateSessionUseCase(delegate, logPort);
        Instant t0 = Instant.parse("2050-01-01T10:00:00Z");
        Instant t1 = Instant.parse("2050-01-01T11:00:00Z");
        CreateSessionCommand cmd = new CreateSessionCommand(EventId.valueOf("evc"), 3, t0, t1);
        SessionId sid = SessionId.valueOf("svc");
        when(delegate.execute(cmd)).thenReturn(sid);

        assertEquals(sid, logging.execute(cmd));

        verify(logPort).info(eq("useCase=CreateSession phase=start eventId={}"), eq("evc"));
        verify(logPort).info(eq("useCase=CreateSession phase=completed sessionId={}"), eq("svc"));
    }
}
