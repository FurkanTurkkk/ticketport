package com.furkan.ticketport.event.incoming.session;

import com.furkan.ticketport.event.dto.request.CreateSessionRequest;
import com.furkan.ticketport.event.dto.response.CreateSessionResponse;
import com.furkan.ticketport.event.dto.response.SessionsListResponse;
import com.furkan.ticketport.event.port.in.session.CreateSessionUseCase;
import com.furkan.ticketport.event.port.in.session.ListSessionsByEventQuery;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.SessionId;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionsController {

    private final CreateSessionUseCase createSessionUseCase;
    private final ListSessionsByEventQuery listSessionsByEventQuery;

    public SessionsController(
            CreateSessionUseCase createSessionUseCase,
            ListSessionsByEventQuery listSessionsByEventQuery) {
        this.createSessionUseCase = createSessionUseCase;
        this.listSessionsByEventQuery = listSessionsByEventQuery;
    }

    @GetMapping
    public ResponseEntity<SessionsListResponse> listByEvent(@RequestParam("eventId") String eventId) {
        return ResponseEntity.ok(
                SessionsListResponse.from(
                        listSessionsByEventQuery.listByEventId(EventId.valueOf(eventId))));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateSessionResponse> createSession(@RequestBody CreateSessionRequest request) {
        SessionId sessionId = createSessionUseCase.execute(request.toCommand());
        return ResponseEntity.ok(new CreateSessionResponse(sessionId.asString()));
    }
}
