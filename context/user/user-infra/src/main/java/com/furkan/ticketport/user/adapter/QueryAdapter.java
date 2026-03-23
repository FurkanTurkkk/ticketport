package com.furkan.ticketport.user.adapter;

import com.furkan.ticketport.user.mapper.UserMapper;
import com.furkan.ticketport.user.model.User;
import com.furkan.ticketport.user.port.out.UserQueryPort;
import com.furkan.ticketport.user.repository.UserJpaRepository;
import com.furkan.ticketport.user.valueobject.Email;
import com.furkan.ticketport.user.valueobject.UserId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class QueryAdapter implements UserQueryPort {

    private final UserJpaRepository userRepository;

    public QueryAdapter(UserJpaRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findUserById(UserId userId) {
        return userRepository.findById(userId.asString()).map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findUserByEmail(Email email) {
        return userRepository.findByEmail(email.asString()).map(UserMapper::toDomain);
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDomain)
                .toList();
    }
}
