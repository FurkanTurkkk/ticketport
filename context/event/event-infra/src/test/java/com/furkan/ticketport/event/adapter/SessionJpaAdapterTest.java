package com.furkan.ticketport.event.adapter;

import com.furkan.ticketport.event.entity.SessionEntity;
import com.furkan.ticketport.event.model.Session;
import com.furkan.ticketport.event.repository.SessionJpaRepository;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.SessionId;
import com.furkan.ticketport.event.valueobject.SessionStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionJpaAdapterTest {

    @Mock
    private SessionJpaRepository sessionJpaRepository;

    @InjectMocks
    private SessionJpaAdapter adapter;

    @Test
    void saveReturnsId() {
        Instant t0 = Instant.parse("2060-01-01T10:00:00Z");
        Instant t1 = Instant.parse("2060-01-01T11:00:00Z");
        Session s = Session.create(SessionId.valueOf("sj1"), EventId.valueOf("evj"), 3, t0, t1);
        SessionEntity saved = new SessionEntity();
        saved.setId("sj1");
        when(sessionJpaRepository.save(ArgumentMatchers.any(SessionEntity.class))).thenReturn(saved);

        assertEquals("sj1", adapter.save(s).asString());
    }

    @Test
    void findBySessionIdAndEventList() {
        Instant t0 = Instant.parse("2060-02-01T10:00:00Z");
        Instant t1 = Instant.parse("2060-02-01T11:00:00Z");
        SessionEntity entity =
                new SessionEntity(
                        "sid",
                        "evj",
                        1,
                        SessionStatus.ON_SALE,
                        t0,
                        t1,
                        t0,
                        t1);
        when(sessionJpaRepository.findById("sid")).thenReturn(Optional.of(entity));
        when(sessionJpaRepository.findByEventIdOrderByStartedAtAsc("evj")).thenReturn(List.of(entity));

        assertTrue(adapter.findBySessionId(SessionId.valueOf("sid")).isPresent());
        assertEquals(1, adapter.findByEventId(EventId.valueOf("evj")).size());

        adapter.delete(Session.restore(
                SessionId.valueOf("sid"),
                EventId.valueOf("evj"),
                1,
                SessionStatus.ON_SALE,
                t0,
                t1,
                t0,
                t1));
        verify(sessionJpaRepository).deleteById("sid");
    }
}
