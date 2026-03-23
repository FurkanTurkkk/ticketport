package com.furkan.ticketport.user.decorator;

import com.furkan.ticketport.user.command.RegisterUserCommand;
import com.furkan.ticketport.user.exception.EmailAlreadyExistsException;
import com.furkan.ticketport.user.port.in.RegisterUserUseCase;
import com.furkan.ticketport.user.transaction.RegisterUserTransactionRunner;
import com.furkan.ticketport.user.valueobject.UserId;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


@Service
public class TransactionalRegisterUserUseCase implements RegisterUserUseCase {

    private final RegisterUserTransactionRunner transactionRunner;

    public TransactionalRegisterUserUseCase(RegisterUserTransactionRunner transactionRunner) {
        this.transactionRunner = transactionRunner;
    }

    @Override
    public UserId execute(RegisterUserCommand cmd) {
        try {
            return transactionRunner.register(cmd);
        } catch (DataIntegrityViolationException e) {
            throw new EmailAlreadyExistsException(
                    String.format("[%s] email already exists.", cmd.email().asString()));
        }
    }
}
