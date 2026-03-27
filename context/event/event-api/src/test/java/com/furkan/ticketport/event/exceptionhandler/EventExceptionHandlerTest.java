package com.furkan.ticketport.event.exceptionhandler;

import com.furkan.ticketport.event.dto.response.ErrorResponse;
import com.furkan.ticketport.event.exception.CategoryNotFoundException;
import com.furkan.ticketport.event.exception.EventCannotBePublishedException;
import com.furkan.ticketport.event.exception.EventNotFoundException;
import com.furkan.ticketport.event.exception.EventNotPublishedException;
import com.furkan.ticketport.event.exception.InvalidCategoryIdException;
import com.furkan.ticketport.event.exception.InvalidCoverImageRefException;
import com.furkan.ticketport.event.exception.InvalidEventIdException;
import com.furkan.ticketport.event.exception.InvalidSessionCapacityException;
import com.furkan.ticketport.event.exception.InvalidSessionIdException;
import com.furkan.ticketport.event.exception.InvalidSessionScheduleException;
import com.furkan.ticketport.event.exception.InvalidSlugException;
import com.furkan.ticketport.event.exception.InvalidTitleException;
import com.furkan.ticketport.event.exception.SessionNotFoundException;
import com.furkan.ticketport.event.exception.SlugImmutableAfterPublishException;
import com.furkan.ticketport.event.exception.UniqueSlugAllocationException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventExceptionHandlerTest {

    private final EventExceptionHandler handler = new EventExceptionHandler();

    @Test
    void eventNotFoundIs404() {
        ResponseEntity<ErrorResponse> r = handler.handleEventNotFound(new EventNotFoundException("e"));
        assertEquals(HttpStatus.NOT_FOUND, r.getStatusCode());
        assertEquals(404, r.getBody().status());
    }

    @Test
    void categoryNotFoundIs404() {
        ResponseEntity<ErrorResponse> r =
                handler.handleCategoryNotFound(new CategoryNotFoundException("c"));
        assertEquals(HttpStatus.NOT_FOUND, r.getStatusCode());
    }

    @Test
    void sessionNotFoundIs404() {
        ResponseEntity<ErrorResponse> r =
                handler.handleSessionNotFound(new SessionNotFoundException("s"));
        assertEquals(HttpStatus.NOT_FOUND, r.getStatusCode());
    }

    @Test
    void eventNotPublishedIs409() {
        ResponseEntity<ErrorResponse> r =
                handler.handleEventNotPublished(new EventNotPublishedException("np"));
        assertEquals(HttpStatus.CONFLICT, r.getStatusCode());
    }

    @Test
    void eventCannotBePublishedIs400() {
        ResponseEntity<ErrorResponse> r =
                handler.handleEventCannotBePublished(new EventCannotBePublishedException("x"));
        assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
    }

    @Test
    void uniqueSlugAllocationIs500() {
        ResponseEntity<ErrorResponse> r =
                handler.handleUniqueSlugAllocation(new UniqueSlugAllocationException("u"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, r.getStatusCode());
        assertEquals(500, r.getBody().status());
    }

    @Test
    void slugImmutableIs409() {
        ResponseEntity<ErrorResponse> r =
                handler.handleSlugImmutable(new SlugImmutableAfterPublishException("im"));
        assertEquals(HttpStatus.CONFLICT, r.getStatusCode());
    }

    @Test
    void badRequestVariants() {
        assert400(handler.handleBadRequest(new InvalidTitleException("t")));
        assert400(handler.handleBadRequest(new InvalidCategoryIdException("ci")));
        assert400(handler.handleBadRequest(new InvalidEventIdException("ei")));
        assert400(handler.handleBadRequest(new InvalidSlugException("s")));
        assert400(handler.handleBadRequest(new InvalidCoverImageRefException("c")));
        assert400(handler.handleBadRequest(new InvalidSessionCapacityException("cap")));
        assert400(handler.handleBadRequest(new InvalidSessionScheduleException("sch")));
        assert400(handler.handleBadRequest(new InvalidSessionIdException("sidbad")));
    }

    private static void assert400(ResponseEntity<ErrorResponse> r) {
        assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
        assertEquals(400, r.getBody().status());
    }
}
