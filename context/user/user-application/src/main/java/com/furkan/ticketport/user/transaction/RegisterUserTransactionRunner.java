package com.furkan.ticketport.user.transaction;

import com.furkan.ticketport.user.command.RegisterUserCommand;
import com.furkan.ticketport.user.usecase.RegisterUserService;
import com.furkan.ticketport.user.valueobject.UserId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RegisterUserTransactionRunner {

    private final RegisterUserService registerUserService;

    public RegisterUserTransactionRunner(RegisterUserService registerUserService) {
        this.registerUserService = registerUserService;
    }

    @Transactional
    public UserId register(RegisterUserCommand cmd) {
        return registerUserService.execute(cmd);
    }
}
