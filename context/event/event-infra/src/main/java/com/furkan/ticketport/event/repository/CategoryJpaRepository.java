package com.furkan.ticketport.event.repository;

import com.furkan.ticketport.event.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, String> {}
