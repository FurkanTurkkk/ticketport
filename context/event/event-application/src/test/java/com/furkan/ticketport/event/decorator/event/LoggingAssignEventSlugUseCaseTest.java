package com.furkan.ticketport.event.decorator.event;

import com.furkan.ticketport.event.command.event.AssignEventSlugCommand;
import com.furkan.ticketport.event.port.out.LogPort;
import com.furkan.ticketport.event.usecase.event.AssignEventSlugService;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.Slug;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoggingAssignEventSlugUseCaseTest {

    @Mock
    private AssignEventSlugService delegate;

    @Mock
    private LogPort logPort;

    @Test
    void logsAroundDelegate() {
        LoggingAssignEventSlugUseCase logging = new LoggingAssignEventSlugUseCase(delegate, logPort);
        AssignEventSlugCommand cmd = new AssignEventSlugCommand(EventId.valueOf("a1"), Slug.valueOf("sl"));
        logging.execute(cmd);
        verify(logPort)
                .info(
                        eq("useCase=AssignEventSlug phase=start eventId={} slug={}"),
                        eq("a1"),
                        eq("sl"));
        verify(delegate).execute(cmd);
        verify(logPort).info(eq("useCase=AssignEventSlug phase=completed eventId={}"), eq("a1"));
    }
}
