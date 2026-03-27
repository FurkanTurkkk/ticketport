package com.furkan.ticketport.event.decorator.event;

import com.furkan.ticketport.event.port.out.LogPort;
import com.furkan.ticketport.event.usecase.event.DeleteEventService;
import com.furkan.ticketport.event.valueobject.EventId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoggingDeleteEventUseCaseTest {

    @Mock
    private DeleteEventService delegate;

    @Mock
    private LogPort logPort;

    @Test
    void logsAroundDelegate() {
        LoggingDeleteEventUseCase logging = new LoggingDeleteEventUseCase(delegate, logPort);
        EventId id = EventId.valueOf("d1");
        logging.execute(id);
        verify(logPort).info(eq("useCase=DeleteEvent phase=start eventId={}"), eq("d1"));
        verify(delegate).execute(id);
        verify(logPort).info(eq("useCase=DeleteEvent phase=completed eventId={}"), eq("d1"));
    }
}
