package com.furkan.ticketport.event.incoming.event;

import com.furkan.ticketport.event.command.event.PublishEventCommand;
import com.furkan.ticketport.event.dto.request.CreateEventRequest;
import com.furkan.ticketport.event.dto.response.CreateEventResponse;
import com.furkan.ticketport.event.dto.response.EventsListResponse;
import com.furkan.ticketport.event.port.in.event.AssignEventSlugUseCase;
import com.furkan.ticketport.event.dto.request.AssignEventSlugRequest;
import com.furkan.ticketport.event.port.in.event.CreateEventUseCase;
import com.furkan.ticketport.event.port.in.event.ListAllEventsQuery;
import com.furkan.ticketport.event.port.in.event.PublishEventUseCase;
import com.furkan.ticketport.event.valueobject.EventId;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/events")
public class EventsController {

    private final CreateEventUseCase createEventUseCase;
    private final AssignEventSlugUseCase assignEventSlugUseCase;
    private final PublishEventUseCase publishEventUseCase;
    private final ListAllEventsQuery listAllEventsQuery;

    public EventsController(
            CreateEventUseCase createEventUseCase,
            AssignEventSlugUseCase assignEventSlugUseCase,
            PublishEventUseCase publishEventUseCase,
            ListAllEventsQuery listAllEventsQuery) {
        this.createEventUseCase = createEventUseCase;
        this.assignEventSlugUseCase = assignEventSlugUseCase;
        this.publishEventUseCase = publishEventUseCase;
        this.listAllEventsQuery = listAllEventsQuery;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateEventResponse> createEvent(@RequestBody CreateEventRequest request) {
        EventId eventId = createEventUseCase.execute(request.toCommand());
        return ResponseEntity.ok(new CreateEventResponse(eventId.asString()));
    }

    @PutMapping("/{eventId}/slug")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> assignSlug(
            @PathVariable("eventId") String eventId, @RequestBody AssignEventSlugRequest request) {
        assignEventSlugUseCase.execute(request.toCommand(eventId));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{eventId}/publish")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> publishEvent(@PathVariable("eventId") String eventId) {
        publishEventUseCase.execute(new PublishEventCommand(EventId.valueOf(eventId)));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list")
    public ResponseEntity<EventsListResponse> listEvents() {
        return ResponseEntity.ok(EventsListResponse.from(listAllEventsQuery.listAll()));
    }
}
