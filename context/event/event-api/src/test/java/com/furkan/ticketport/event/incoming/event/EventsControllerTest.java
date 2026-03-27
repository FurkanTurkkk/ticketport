package com.furkan.ticketport.event.incoming.event;

import com.furkan.ticketport.event.port.in.event.AssignEventSlugUseCase;
import com.furkan.ticketport.event.port.in.event.CreateEventUseCase;
import com.furkan.ticketport.event.port.in.event.ListAllEventsQuery;
import com.furkan.ticketport.event.port.in.event.PublishEventUseCase;
import com.furkan.ticketport.event.model.Event;
import com.furkan.ticketport.event.valueobject.CategoryId;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.EventStatus;
import com.furkan.ticketport.event.valueobject.Title;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class EventsControllerTest {

    @Mock
    private CreateEventUseCase createEventUseCase;

    @Mock
    private AssignEventSlugUseCase assignEventSlugUseCase;

    @Mock
    private PublishEventUseCase publishEventUseCase;

    @Mock
    private ListAllEventsQuery listAllEventsQuery;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc =
                MockMvcBuilders.standaloneSetup(
                                new EventsController(
                                        createEventUseCase,
                                        assignEventSlugUseCase,
                                        publishEventUseCase,
                                        listAllEventsQuery))
                        .build();
    }

    @Test
    void createReturnsEventId() throws Exception {
        when(createEventUseCase.execute(any())).thenReturn(EventId.valueOf("new-id"));

        mockMvc.perform(
                        post("/api/v1/events")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                        {"categoryId":"b0000001-0000-4000-8000-000000000001","title":"Api Event","description":null,"coverImageRef":null}
                                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId").value("new-id"));
    }

    @Test
    void assignSlugNoContent() throws Exception {
        mockMvc.perform(
                        put("/api/v1/events/e1/slug")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"slug\":\"nice-slug\"}"))
                .andExpect(status().isNoContent());
        verify(assignEventSlugUseCase).execute(any());
    }

    @Test
    void publishNoContent() throws Exception {
        mockMvc.perform(post("/api/v1/events/e1/publish")).andExpect(status().isNoContent());
        verify(publishEventUseCase).execute(any());
    }

    @Test
    void listEvents() throws Exception {
        Instant n = Instant.parse("2025-01-01T00:00:00Z");
        Event e =
                Event.restore(
                        EventId.valueOf("le1"),
                        CategoryId.valueOf("b0000001-0000-4000-8000-000000000001"),
                        Title.valueOf("Listed"),
                        "listed",
                        "d",
                        null,
                        null,
                        EventStatus.DRAFT,
                        n,
                        n);
        when(listAllEventsQuery.listAll()).thenReturn(List.of(e));

        mockMvc.perform(get("/api/v1/events/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.events[0].id").value("le1"))
                .andExpect(jsonPath("$.events[0].title").value("Listed"));
    }
}
