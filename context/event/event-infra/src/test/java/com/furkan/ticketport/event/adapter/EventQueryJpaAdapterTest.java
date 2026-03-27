package com.furkan.ticketport.event.adapter;

import com.furkan.ticketport.event.entity.EventEntity;
import com.furkan.ticketport.event.model.Category;
import com.furkan.ticketport.event.repository.EventJpaRepository;
import com.furkan.ticketport.event.valueobject.CategoryId;
import com.furkan.ticketport.event.valueobject.CategoryType;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.EventStatus;
import com.furkan.ticketport.event.valueobject.Slug;
import com.furkan.ticketport.event.valueobject.Title;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventQueryJpaAdapterTest {

    @Mock
    private EventJpaRepository eventJpaRepository;

    @InjectMocks
    private EventQueryJpaAdapter adapter;

    @Test
    void findByEventId() {
        EventEntity entity = sampleEntity("e1");
        when(eventJpaRepository.findById("e1")).thenReturn(Optional.of(entity));
        assertTrue(adapter.findByEventId(EventId.valueOf("e1")).isPresent());
    }

    @Test
    void findBySlug() {
        EventEntity entity = sampleEntity("e2");
        when(eventJpaRepository.findBySlug("sl")).thenReturn(Optional.of(entity));
        assertTrue(adapter.findBySlug(Slug.valueOf("sl")).isPresent());
    }

    @Test
    void findByEventTitleReturnsEmptyUnlessSingleMatch() {
        Title t = Title.valueOf("Ambiguous");
        when(eventJpaRepository.findByTitle(t.asString())).thenReturn(List.of());
        assertTrue(adapter.findByEventTitle(t).isEmpty());

        when(eventJpaRepository.findByTitle(t.asString()))
                .thenReturn(List.of(sampleEntity("a"), sampleEntity("b")));
        assertTrue(adapter.findByEventTitle(t).isEmpty());

        when(eventJpaRepository.findByTitle(t.asString())).thenReturn(List.of(sampleEntity("solo")));
        assertTrue(adapter.findByEventTitle(t).isPresent());
    }

    @Test
    void findByCategoryAndFindAll() {
        Category cat = Category.create(CategoryId.valueOf("cx"), CategoryType.TRIP);
        EventEntity entity = sampleEntity("e3");
        when(eventJpaRepository.findByCategoryId("cx")).thenReturn(List.of(entity));
        when(eventJpaRepository.findAll()).thenReturn(List.of(entity));

        assertEquals(1, adapter.findByCategory(cat).size());
        assertEquals(1, adapter.findAll().size());
    }

    private static EventEntity sampleEntity(String id) {
        Instant n = Instant.now();
        return new EventEntity(
                id,
                "cat",
                "Title",
                "title",
                null,
                null,
                "sluggy",
                EventStatus.DRAFT,
                n,
                n);
    }
}
