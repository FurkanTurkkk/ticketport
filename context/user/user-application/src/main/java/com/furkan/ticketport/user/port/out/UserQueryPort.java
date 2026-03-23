package com.furkan.ticketport.user.port.out;

import com.furkan.ticketport.user.model.User;
import com.furkan.ticketport.user.valueobject.Email;
import com.furkan.ticketport.user.valueobject.UserId;

import java.util.List;
import java.util.Optional;

public interface UserQueryPort {
    Optional<User> findUserById(UserId userId);
    Optional<User> findUserByEmail(Email email);
    List<User> findAllUser();
}
