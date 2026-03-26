package com.furkan.ticketport.event.command.event;

import com.furkan.ticketport.event.valueobject.CategoryId;
import com.furkan.ticketport.event.valueobject.Title;

public record CreateEventCommand(
        CategoryId categoryId, Title title, String description, String coverImageRef) {}
