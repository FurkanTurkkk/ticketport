package com.furkan.ticketport.user.adapter;

import com.furkan.ticketport.user.entity.UserEntity;
import com.furkan.ticketport.user.model.User;
import com.furkan.ticketport.user.repository.UserJpaRepository;
import com.furkan.ticketport.user.valueobject.Email;
import com.furkan.ticketport.user.valueobject.Password;
import com.furkan.ticketport.user.valueobject.UserId;
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
class UserJpaAdapterTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @InjectMocks
    private UserJpaAdapter adapter;

    @Test
    void saveReturnsPersistedId() {
        User user =
                User.create(
                        UserId.valueOf("u-1"),
                        Email.valueOf("a@b.com"),
                        Password.fromStoredHash("$2a$10$xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"));
        UserEntity saved = new UserEntity();
        saved.setId("u-1");
        when(userJpaRepository.save(ArgumentMatchers.any(UserEntity.class))).thenReturn(saved);

        assertEquals("u-1", adapter.save(user).asString());

        verify(userJpaRepository).save(ArgumentMatchers.any(UserEntity.class));
    }
}
