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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EventExceptionHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEventNotFound(EventNotFoundException e) {
        ErrorResponse body = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFound(CategoryNotFoundException e) {
        ErrorResponse body = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(SessionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSessionNotFound(SessionNotFoundException e) {
        ErrorResponse body = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(EventNotPublishedException.class)
    public ResponseEntity<ErrorResponse> handleEventNotPublished(EventNotPublishedException e) {
        ErrorResponse body = new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(EventCannotBePublishedException.class)
    public ResponseEntity<ErrorResponse> handleEventCannotBePublished(EventCannotBePublishedException e) {
        ErrorResponse body = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(UniqueSlugAllocationException.class)
    public ResponseEntity<ErrorResponse> handleUniqueSlugAllocation(UniqueSlugAllocationException e) {
        ErrorResponse body = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(SlugImmutableAfterPublishException.class)
    public ResponseEntity<ErrorResponse> handleSlugImmutable(SlugImmutableAfterPublishException e) {
        ErrorResponse body = new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler({
        InvalidTitleException.class,
        InvalidCategoryIdException.class,
        InvalidEventIdException.class,
        InvalidSlugException.class,
        InvalidCoverImageRefException.class,
        InvalidSessionCapacityException.class,
        InvalidSessionScheduleException.class,
        InvalidSessionIdException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(RuntimeException e) {
        ErrorResponse body = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
