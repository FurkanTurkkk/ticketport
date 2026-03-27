package com.furkan.ticketport.event.decorator.event;

import com.furkan.ticketport.event.command.event.CreateEventCommand;
import com.furkan.ticketport.event.port.out.LogPort;
import com.furkan.ticketport.event.testsupport.EventTestFixtures;
import com.furkan.ticketport.event.usecase.event.CreateEventService;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.Title;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoggingCreateEventUseCaseTest {

    @Mock
    private CreateEventService delegate;

    @Mock
    private LogPort logPort;

    @Test
    void logsAroundDelegate() {
        LoggingCreateEventUseCase logging = new LoggingCreateEventUseCase(delegate, logPort);
        CreateEventCommand cmd =
                new CreateEventCommand(EventTestFixtures.SEED_CATEGORY, Title.valueOf("L"), null, null);
        EventId id = EventId.valueOf("e-log");
        when(delegate.execute(cmd)).thenReturn(id);

        assertEquals(id, logging.execute(cmd));

        verify(logPort)
                .info(
                        eq("useCase=CreateEvent phase=start categoryId={} titleLength={}"),
                        eq(EventTestFixtures.SEED_CATEGORY.asString()),
                        eq(1));
        verify(logPort).info(eq("useCase=CreateEvent phase=completed eventId={}"), eq("e-log"));
    }
}
