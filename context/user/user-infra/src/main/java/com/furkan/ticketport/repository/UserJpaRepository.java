package com.furkan.ticketport.repository;

import com.furkan.ticketport.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, String> {}
