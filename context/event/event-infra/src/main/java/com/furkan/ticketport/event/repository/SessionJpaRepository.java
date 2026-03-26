package com.furkan.ticketport.event.repository;

import com.furkan.ticketport.event.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionJpaRepository extends JpaRepository<SessionEntity, String> {

    List<SessionEntity> findByEventIdOrderByStartedAtAsc(String eventId);
}
