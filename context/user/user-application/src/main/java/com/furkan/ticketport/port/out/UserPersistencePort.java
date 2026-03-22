package com.furkan.ticketport.port.out;

import com.furkan.ticketport.model.User;
import com.furkan.ticketport.valueobject.UserId;

public interface UserPersistencePort {
    UserId save(User user);
    void delete(User user);
}
