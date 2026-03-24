package com.furkan.ticketport.user.adapter;

import com.furkan.ticketport.user.entity.UserEntity;
import com.furkan.ticketport.user.repository.UserJpaRepository;
import com.furkan.ticketport.user.valueobject.Email;
import com.furkan.ticketport.user.valueobject.Role;
import com.furkan.ticketport.user.valueobject.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueryAdapterTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @InjectMocks
    private QueryAdapter adapter;

    @Test
    void findUserByEmailMapsEntity() {
        UserEntity entity = new UserEntity();
        entity.setId("i1");
        entity.setEmail("e@e.com");
        entity.setPassword("$2a$10$hash");
        entity.setRole(Role.ROLE_USER);
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());
        when(userJpaRepository.findByEmail("e@e.com")).thenReturn(Optional.of(entity));

        var found = adapter.findUserByEmail(Email.valueOf("e@e.com"));

        assertTrue(found.isPresent());
        assertEquals("i1", found.get().userId().asString());
    }

    @Test
    void findUserByIdDelegates() {
        UserEntity entity = new UserEntity();
        entity.setId("i1");
        entity.setEmail("e@e.com");
        entity.setPassword("$2a$10$hash");
        entity.setRole(Role.ROLE_USER);
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());
        when(userJpaRepository.findById("i1")).thenReturn(Optional.of(entity));

        assertTrue(adapter.findUserById(UserId.valueOf("i1")).isPresent());
    }
}
