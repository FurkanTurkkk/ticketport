package com.furkan.ticketport.event.command.event;

import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.Slug;

public record AssignEventSlugCommand(EventId eventId, Slug slug) {}
