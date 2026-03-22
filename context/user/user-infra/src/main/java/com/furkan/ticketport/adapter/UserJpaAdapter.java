package com.furkan.ticketport.adapter;

import com.furkan.ticketport.entity.UserEntity;
import com.furkan.ticketport.exception.EmailAlreadyExistsException;
import com.furkan.ticketport.mapper.UserMapper;
import com.furkan.ticketport.model.User;
import com.furkan.ticketport.port.out.UserPersistencePort;
import com.furkan.ticketport.repository.UserJpaRepository;
import com.furkan.ticketport.valueobject.UserId;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

@Repository
public class UserJpaAdapter implements UserPersistencePort {

    private final UserJpaRepository userRepository;

    public UserJpaAdapter(UserJpaRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserId save(User user) {
        try {
            UserEntity entity = userRepository.saveAndFlush(UserMapper.fromDomain(user));
            return UserId.valueOf(entity.getId());
        } catch (DataIntegrityViolationException e) {
            throw new EmailAlreadyExistsException(
                    String.format("[%s] email already exists.", user.email().asString()));
        }
    }

    @Override
    public void delete(User user) {
        userRepository.delete(UserMapper.fromDomain(user));
    }
}
