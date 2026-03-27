package com.furkan.ticketport.event.decorator.event;

import com.furkan.ticketport.event.command.event.UnpublishEventCommand;
import com.furkan.ticketport.event.port.out.LogPort;
import com.furkan.ticketport.event.usecase.event.UnpublishEventService;
import com.furkan.ticketport.event.valueobject.EventId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoggingUnpublishEventUseCaseTest {

    @Mock
    private UnpublishEventService delegate;

    @Mock
    private LogPort logPort;

    @Test
    void logsAroundDelegate() {
        LoggingUnpublishEventUseCase logging = new LoggingUnpublishEventUseCase(delegate, logPort);
        UnpublishEventCommand cmd = new UnpublishEventCommand(EventId.valueOf("u1"));
        logging.execute(cmd);
        verify(logPort).info(eq("useCase=UnpublishEvent phase=start eventId={}"), eq("u1"));
        verify(delegate).execute(cmd);
        verify(logPort).info(eq("useCase=UnpublishEvent phase=completed eventId={}"), eq("u1"));
    }
}
