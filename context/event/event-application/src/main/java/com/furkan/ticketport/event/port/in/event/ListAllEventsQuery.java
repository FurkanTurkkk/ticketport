package com.furkan.ticketport.event.port.in.event;

import com.furkan.ticketport.event.model.Event;

import java.util.List;

public interface ListAllEventsQuery {

    List<Event> listAll();
}
