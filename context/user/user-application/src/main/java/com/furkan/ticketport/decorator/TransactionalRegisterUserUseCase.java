package com.furkan.ticketport.decorator;

import com.furkan.ticketport.command.RegisterUserCommand;
import com.furkan.ticketport.port.in.RegisterUserUseCase;
import com.furkan.ticketport.usecase.RegisterUserService;
import com.furkan.ticketport.valueobject.UserId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionalRegisterUserUseCase implements RegisterUserUseCase {

    private final RegisterUserService registerUserService;

    public TransactionalRegisterUserUseCase(RegisterUserService registerUserService) {
        this.registerUserService = registerUserService;
    }

    @Override
    @Transactional
    public UserId execute(RegisterUserCommand cmd) {
        return registerUserService.execute(cmd);
    }
}
