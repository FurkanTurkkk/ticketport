package com.furkan.ticketport.event.testsupport;

import com.furkan.ticketport.event.model.Event;
import com.furkan.ticketport.event.valueobject.CategoryId;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.Title;

public final class EventTestFixtures {

    public static final CategoryId SEED_CATEGORY =
            CategoryId.valueOf("b0000001-0000-4000-8000-000000000001");

    private EventTestFixtures() {}

    public static Event draftEvent(String title) {
        return Event.create(EventId.valueOf("e0000001-0000-4000-8000-000000000099"), SEED_CATEGORY, Title.valueOf(title));
    }
}
