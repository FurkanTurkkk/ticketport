package com.furkan.ticketport.event.decorator.event;

import com.furkan.ticketport.event.command.event.PublishEventCommand;
import com.furkan.ticketport.event.port.out.LogPort;
import com.furkan.ticketport.event.usecase.event.PublishEventService;
import com.furkan.ticketport.event.valueobject.EventId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoggingPublishEventUseCaseTest {

    @Mock
    private PublishEventService delegate;

    @Mock
    private LogPort logPort;

    @Test
    void logsAroundDelegate() {
        LoggingPublishEventUseCase logging = new LoggingPublishEventUseCase(delegate, logPort);
        PublishEventCommand cmd = new PublishEventCommand(EventId.valueOf("p1"));
        logging.execute(cmd);
        verify(logPort).info(eq("useCase=PublishEvent phase=start eventId={}"), eq("p1"));
        verify(delegate).execute(cmd);
        verify(logPort).info(eq("useCase=PublishEvent phase=completed eventId={}"), eq("p1"));
    }
}
