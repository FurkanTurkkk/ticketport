package com.furkan.ticketport.event.port.in.event;

import com.furkan.ticketport.event.command.event.AssignEventSlugCommand;

public interface AssignEventSlugUseCase {

    void execute(AssignEventSlugCommand cmd);
}
