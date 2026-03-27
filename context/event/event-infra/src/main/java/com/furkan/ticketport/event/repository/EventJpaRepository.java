package com.furkan.ticketport.event.repository;

import com.furkan.ticketport.event.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventJpaRepository extends JpaRepository<EventEntity, String> {

    List<EventEntity> findByTitle(String title);

    List<EventEntity> findByCategoryId(String categoryId);

    Optional<EventEntity> findBySlug(String slug);
}
