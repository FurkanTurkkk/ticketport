package com.furkan.ticketport.port.in;

import com.furkan.ticketport.command.RegisterUserCommand;
import com.furkan.ticketport.valueobject.UserId;

public interface RegisterUserUseCase {
    UserId execute(RegisterUserCommand cmd);
}
