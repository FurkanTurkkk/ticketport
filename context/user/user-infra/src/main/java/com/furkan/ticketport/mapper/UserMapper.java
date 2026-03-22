package com.furkan.ticketport.mapper;

import com.furkan.ticketport.entity.UserEntity;
import com.furkan.ticketport.model.User;

public final class UserMapper {

    private UserMapper() {}

    public static UserEntity fromDomain(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.userId().asString());
        entity.setEmail(user.email().asString());
        entity.setPassword(user.password().asString());
        entity.setRole(user.role());
        entity.setCreatedAt(user.createdAt());
        entity.setUpdatedAt(user.updatedAt());
        return entity;
    }
}
