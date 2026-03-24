package com.furkan.ticketport.user.mapper;

import com.furkan.ticketport.user.entity.UserEntity;
import com.furkan.ticketport.user.model.User;
import com.furkan.ticketport.user.valueobject.Email;
import com.furkan.ticketport.user.valueobject.Password;
import com.furkan.ticketport.user.valueobject.Role;
import com.furkan.ticketport.user.valueobject.UserId;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    @Test
    void roundTripDomainToEntityAndBack() {
        Instant now = Instant.parse("2020-01-01T00:00:00Z");
        User original =
                User.create(
                        UserId.valueOf("id-1"),
                        Email.valueOf("m@x.com"),
                        Password.fromStoredHash("$2a$10$xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"));
        UserEntity entity = UserMapper.fromDomain(original);
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);

        User back = UserMapper.toDomain(entity);

        assertEquals(original.userId().asString(), back.userId().asString());
        assertEquals(original.email().asString(), back.email().asString());
        assertEquals(original.password().asString(), back.password().asString());
        assertEquals(Role.ROLE_USER, back.role());
    }
}
