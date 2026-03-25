package com.furkan.ticketport.event.command.event;

import com.furkan.ticketport.event.valueobject.EventId;

public record PublishEventCommand(EventId eventId) {}
