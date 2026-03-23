package com.furkan.ticketport.user.port.in;

import com.furkan.ticketport.user.command.RegisterUserCommand;
import com.furkan.ticketport.user.valueobject.UserId;

public interface RegisterUserUseCase {
    UserId execute(RegisterUserCommand cmd);
}
