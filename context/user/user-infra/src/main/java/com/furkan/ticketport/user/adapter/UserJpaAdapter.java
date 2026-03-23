package com.furkan.ticketport.user.adapter;

import com.furkan.ticketport.user.entity.UserEntity;
import com.furkan.ticketport.user.mapper.UserMapper;
import com.furkan.ticketport.user.model.User;
import com.furkan.ticketport.user.port.out.UserPersistencePort;
import com.furkan.ticketport.user.repository.UserJpaRepository;
import com.furkan.ticketport.user.valueobject.UserId;
import org.springframework.stereotype.Component;

@Component
public class UserJpaAdapter implements UserPersistencePort {

    private final UserJpaRepository userRepository;

    public UserJpaAdapter(UserJpaRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserId save(User user) {
        UserEntity entity = userRepository.save(UserMapper.fromDomain(user));
        return UserId.valueOf(entity.getId());
    }

    @Override
    public void delete(User user) {
        userRepository.delete(UserMapper.fromDomain(user));
    }
}
