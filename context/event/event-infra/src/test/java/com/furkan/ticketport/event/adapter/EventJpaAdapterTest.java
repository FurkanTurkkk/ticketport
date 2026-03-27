package com.furkan.ticketport.event.adapter;

import com.furkan.ticketport.event.entity.EventEntity;
import com.furkan.ticketport.event.model.Event;
import com.furkan.ticketport.event.repository.EventJpaRepository;
import com.furkan.ticketport.event.valueobject.CategoryId;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.Title;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventJpaAdapterTest {

    @Mock
    private EventJpaRepository eventJpaRepository;

    @InjectMocks
    private EventJpaAdapter adapter;

    @Test
    void saveReturnsPersistedId() {
        Event event =
                Event.create(
                        EventId.valueOf("ej1"),
                        CategoryId.valueOf("cat1"),
                        Title.valueOf("Jpa Event"));
        EventEntity saved = new EventEntity();
        saved.setId("ej1");
        when(eventJpaRepository.save(ArgumentMatchers.any(EventEntity.class))).thenReturn(saved);

        assertEquals("ej1", adapter.save(event).asString());
        verify(eventJpaRepository).save(ArgumentMatchers.any(EventEntity.class));
    }

    @Test
    void deleteById() {
        Event event =
                Event.create(EventId.valueOf("ej2"), CategoryId.valueOf("cat1"), Title.valueOf("Del"));
        adapter.delete(event);
        verify(eventJpaRepository).deleteById("ej2");
    }
}
