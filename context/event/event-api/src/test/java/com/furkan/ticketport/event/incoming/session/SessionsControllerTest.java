package com.furkan.ticketport.event.incoming.session;

import com.furkan.ticketport.event.model.Session;
import com.furkan.ticketport.event.port.in.session.CreateSessionUseCase;
import com.furkan.ticketport.event.port.in.session.ListSessionsByEventQuery;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.SessionId;
import com.furkan.ticketport.event.valueobject.SessionStatus;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SessionsControllerTest {

    @Mock
    private CreateSessionUseCase createSessionUseCase;

    @Mock
    private ListSessionsByEventQuery listSessionsByEventQuery;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc =
                MockMvcBuilders.standaloneSetup(
                                new SessionsController(createSessionUseCase, listSessionsByEventQuery))
                        .build();
    }

    @Test
    void listByEvent() throws Exception {
        Instant t0 = Instant.parse("2026-01-01T10:00:00Z");
        Instant t1 = Instant.parse("2026-01-01T11:00:00Z");
        Session s =
                Session.restore(
                        SessionId.valueOf("ls1"),
                        EventId.valueOf("ev1"),
                        5,
                        SessionStatus.ON_SALE,
                        t0,
                        t1,
                        t0,
                        t1);
        when(listSessionsByEventQuery.listByEventId(any())).thenReturn(List.of(s));

        mockMvc.perform(get("/api/v1/sessions").param("eventId", "eeee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessions[0].id").value("ls1"));
    }

    @Test
    void createSession() throws Exception {
        when(createSessionUseCase.execute(any())).thenReturn(SessionId.valueOf("created"));

        mockMvc.perform(
                        post("/api/v1/sessions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                        {"eventId":"ev99","capacity":10,"startedAt":"2026-06-01T10:00:00Z","endsAt":"2026-06-01T12:00:00Z"}
                                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessionId").value("created"));
        verify(createSessionUseCase).execute(any());
    }
}
