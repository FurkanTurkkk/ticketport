package com.furkan.ticketport.user.exceptionhandler;

import com.furkan.ticketport.user.dto.response.ErrorResponse;
import com.furkan.ticketport.user.exception.EmailAlreadyExistsException;
import com.furkan.ticketport.user.exception.EmailNotFoundException;
import com.furkan.ticketport.user.exception.InvalidPasswordException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserExceptionHandlerTest {

    private final UserExceptionHandler handler = new UserExceptionHandler();

    @Test
    void emailAlreadyExistsIs409() {
        ResponseEntity<ErrorResponse> r =
                handler.handleEmailAlreadyExists(new EmailAlreadyExistsException("dup"));
        assertEquals(HttpStatus.CONFLICT, r.getStatusCode());
        assertEquals(409, r.getBody().status());
    }

    @Test
    void emailNotFoundIs404() {
        ResponseEntity<ErrorResponse> r =
                handler.handleEmailNotFoundException(new EmailNotFoundException("missing"));
        assertEquals(HttpStatus.NOT_FOUND, r.getStatusCode());
        assertEquals(404, r.getBody().status());
    }

    @Test
    void invalidPasswordIs401() {
        ResponseEntity<ErrorResponse> r =
                handler.handleInvalidPasswordException(new InvalidPasswordException("bad"));
        assertEquals(HttpStatus.UNAUTHORIZED, r.getStatusCode());
        assertEquals(401, r.getBody().status());
    }
}
