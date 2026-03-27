package com.furkan.ticketport.event.dto;

import com.furkan.ticketport.event.command.event.AssignEventSlugCommand;
import com.furkan.ticketport.event.command.event.CreateEventCommand;
import com.furkan.ticketport.event.command.session.CreateSessionCommand;
import com.furkan.ticketport.event.dto.request.AssignEventSlugRequest;
import com.furkan.ticketport.event.dto.request.CreateEventRequest;
import com.furkan.ticketport.event.dto.request.CreateSessionRequest;
import com.furkan.ticketport.event.dto.response.ErrorResponse;
import com.furkan.ticketport.event.dto.response.EventResponse;
import com.furkan.ticketport.event.dto.response.EventsListResponse;
import com.furkan.ticketport.event.dto.response.SessionResponse;
import com.furkan.ticketport.event.dto.response.SessionsListResponse;
import com.furkan.ticketport.event.model.Event;
import com.furkan.ticketport.event.model.Session;
import com.furkan.ticketport.event.valueobject.CategoryId;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.EventStatus;
import com.furkan.ticketport.event.valueobject.SessionId;
import com.furkan.ticketport.event.valueobject.SessionStatus;
import com.furkan.ticketport.event.valueobject.Title;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventDtoMappingTest {

    private static final CategoryId CAT =
            CategoryId.valueOf("b0000001-0000-4000-8000-000000000001");

    @Test
    void createEventRequestToCommand() {
        CreateEventCommand cmd =
                new CreateEventRequest(
                                CAT.asString(),
                                "Title",
                                "desc",
                                "https://cover")
                        .toCommand();
        assertEquals("desc", cmd.description());
        assertEquals("https://cover", cmd.coverImageRef());
    }

    @Test
    void assignSlugRequestToCommand() {
        AssignEventSlugCommand cmd =
                new AssignEventSlugRequest("my-slug").toCommand("ev-1");
        assertEquals("ev-1", cmd.eventId().asString());
        assertEquals("my-slug", cmd.slug().asString());
    }

    @Test
    void createSessionRequestToCommand() {
        Instant s = Instant.parse("2027-01-01T10:00:00Z");
        Instant e = Instant.parse("2027-01-01T12:00:00Z");
        CreateSessionCommand cmd =
                new CreateSessionRequest("ev-session", 4, s, e).toCommand();
        assertEquals(4, cmd.capacity());
    }

    @Test
    void responseRecordsFromModels() {
        Instant n = Instant.parse("2028-01-01T00:00:00Z");
        Event domain =
                Event.restore(
                        EventId.valueOf("de1"),
                        CAT,
                        Title.valueOf("DR"),
                        "dr",
                        null,
                        null,
                        null,
                        EventStatus.DRAFT,
                        n,
                        n);
        EventResponse er = EventResponse.from(domain);
        assertEquals("de1", er.id());
        assertEquals(List.of(new EventResponse(
                        er.id(),
                        er.categoryId(),
                        er.title(),
                        er.normalizedTitle(),
                        er.description(),
                        er.coverImageRef(),
                        er.slug(),
                        er.status(),
                        er.createdAt(),
                        er.updatedAt())),
                EventsListResponse.from(List.of(domain)).events());

        Instant t0 = Instant.parse("2028-02-01T10:00:00Z");
        Instant t1 = Instant.parse("2028-02-01T11:00:00Z");
        Session session =
                Session.create(SessionId.valueOf("s-dto"), EventId.valueOf("e-dto"), 2, t0, t1);
        SessionResponse sr = SessionResponse.from(session);
        SessionsListResponse sl =
                SessionsListResponse.from(List.of(session));
        assertEquals(sr.id(), sl.sessions().get(0).id());
    }

    @Test
    void errorResponseRecord() {
        ErrorResponse err = new ErrorResponse("msg", 418);
        assertEquals("msg", err.message());
        assertEquals(418, err.status());
    }
}
