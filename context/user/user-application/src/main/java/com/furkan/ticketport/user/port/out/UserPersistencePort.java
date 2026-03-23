package com.furkan.ticketport.user.port.out;

import com.furkan.ticketport.user.model.User;
import com.furkan.ticketport.user.valueobject.UserId;

public interface UserPersistencePort {
    UserId save(User user);
    void delete(User user);
}
