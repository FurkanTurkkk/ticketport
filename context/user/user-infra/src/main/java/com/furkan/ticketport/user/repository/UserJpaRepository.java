package com.furkan.ticketport.user.repository;

import com.furkan.ticketport.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, String> {}
