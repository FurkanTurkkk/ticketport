package com.furkan.ticketport.user.port.in;

import com.furkan.ticketport.user.command.LoginUserCommand;
import com.furkan.ticketport.user.result.LoginResult;

public interface LoginUserUseCase {
    LoginResult execute(LoginUserCommand cmd);
}
