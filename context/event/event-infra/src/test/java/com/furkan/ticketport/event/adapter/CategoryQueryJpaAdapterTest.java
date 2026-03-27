package com.furkan.ticketport.event.adapter;

import com.furkan.ticketport.event.entity.CategoryEntity;
import com.furkan.ticketport.event.repository.CategoryJpaRepository;
import com.furkan.ticketport.event.valueobject.CategoryId;
import com.furkan.ticketport.event.valueobject.CategoryType;
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
class CategoryQueryJpaAdapterTest {

    @Mock
    private CategoryJpaRepository categoryJpaRepository;

    @InjectMocks
    private CategoryQueryJpaAdapter adapter;

    @Test
    void findAllAndById() {
        Instant now = Instant.now();
        CategoryEntity e = new CategoryEntity("c1", CategoryType.MOVIE, now, now);
        when(categoryJpaRepository.findAll()).thenReturn(List.of(e));
        when(categoryJpaRepository.findById("c1")).thenReturn(Optional.of(e));

        assertEquals(1, adapter.findAll().size());
        assertTrue(adapter.findById(CategoryId.valueOf("c1")).isPresent());
    }
}
