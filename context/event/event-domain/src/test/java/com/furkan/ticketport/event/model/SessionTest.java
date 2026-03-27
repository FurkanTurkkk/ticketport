package com.furkan.ticketport.event.model;

import com.furkan.ticketport.event.exception.InvalidSessionCapacityException;
import com.furkan.ticketport.event.exception.InvalidSessionScheduleException;
import com.furkan.ticketport.event.testsupport.EventTestModels;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.SessionId;
import com.furkan.ticketport.event.valueobject.SessionStatus;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SessionTest {

    private static final Instant T0 = Instant.parse("2030-06-01T18:00:00Z");
    private static final Instant T1 = Instant.parse("2030-06-01T20:00:00Z");

    @Test
    void createSuccess() {
        var host = EventTestModels.draftEvent("E");
        Session s = Session.create(SessionId.valueOf("s1"), host.eventId(), 10, T0, T1);
        assertEquals("s1", s.sessionId().asString());
        assertEquals(host.eventId().asString(), s.eventId().asString());
        assertEquals(T1, s.endsAt());
        assertEquals(SessionStatus.ON_SALE, s.status());
        assertEquals(10, s.capacity());
        assertNotNull(s.createdAt());
    }

    @Test
    void createRejectsCapacityBelowOne() {
        assertThrows(
                InvalidSessionCapacityException.class,
                () ->
                        Session.create(
                                SessionId.valueOf("s2"), EventId.valueOf("ev"), 0, T0, T1));
    }

    @Test
    void createRejectsNullSchedule() {
        assertThrows(
                InvalidSessionScheduleException.class,
                () ->
                        Session.create(SessionId.valueOf("s3"), EventId.valueOf("ev"), 1, null, T1));
        assertThrows(
                InvalidSessionScheduleException.class,
                () ->
                        Session.create(SessionId.valueOf("s4"), EventId.valueOf("ev"), 1, T0, null));
    }

    @Test
    void createRejectsEndNotAfterStart() {
        assertThrows(
                InvalidSessionScheduleException.class,
                () ->
                        Session.create(SessionId.valueOf("s5"), EventId.valueOf("ev"), 1, T1, T0));
        assertThrows(
                InvalidSessionScheduleException.class,
                () ->
                        Session.create(SessionId.valueOf("s6"), EventId.valueOf("ev"), 1, T0, T0));
    }

    @Test
    void restoreValidates() {
        Instant c = Instant.parse("2021-01-01T00:00:00Z");
        Instant u = Instant.parse("2021-01-02T00:00:00Z");
        Session s =
                Session.restore(
                        SessionId.valueOf("s7"),
                        EventId.valueOf("ev"),
                        5,
                        SessionStatus.CANCELLED,
                        T0,
                        T1,
                        c,
                        u);
        assertEquals(c, s.createdAt());
        assertEquals(u, s.updatedAt());
        assertEquals(SessionStatus.CANCELLED, s.status());
    }

    @Test
    void rescheduleCancelSoldOutResume() {
        Session s = Session.create(SessionId.valueOf("s8"), EventId.valueOf("ev"), 3, T0, T1);
        Instant t2 = Instant.parse("2030-07-01T18:00:00Z");
        Instant t3 = Instant.parse("2030-07-01T21:00:00Z");
        s.reschedule(t2, t3);
        assertEquals(t2, s.startedAt());
        s.cancel();
        assertEquals(SessionStatus.CANCELLED, s.status());
        Session s2 = Session.create(SessionId.valueOf("s9"), EventId.valueOf("ev"), 2, T0, T1);
        s2.markSoldOut();
        assertEquals(SessionStatus.SOLD_OUT, s2.status());
        s2.resumeSaleIfPossible();
        assertEquals(SessionStatus.ON_SALE, s2.status());
        s2.resumeSaleIfPossible();
        assertEquals(SessionStatus.ON_SALE, s2.status());
    }
}
