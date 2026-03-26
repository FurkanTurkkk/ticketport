package com.furkan.ticketport.event.dto.request;

import com.furkan.ticketport.event.command.event.CreateEventCommand;
import com.furkan.ticketport.event.valueobject.CategoryId;
import com.furkan.ticketport.event.valueobject.Title;

public record CreateEventRequest(
        String categoryId, String title, String description, String coverImageRef) {

    public CreateEventCommand toCommand() {
        return new CreateEventCommand(
                CategoryId.valueOf(categoryId),
                Title.valueOf(title),
                description,
                coverImageRef);
    }
}
