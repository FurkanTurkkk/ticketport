package com.furkan.ticketport.decorator;

import com.furkan.ticketport.command.RegisterUserCommand;
import com.furkan.ticketport.port.in.RegisterUserUseCase;
import com.furkan.ticketport.port.out.LogPort;
import com.furkan.ticketport.valueobject.UserId;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class LoggingRegisterUserUseCase implements RegisterUserUseCase {

    private final TransactionalRegisterUserUseCase delegate;
    private final LogPort logPort;

    public LoggingRegisterUserUseCase(
            TransactionalRegisterUserUseCase delegate, LogPort logPort) {
        this.delegate = delegate;
        this.logPort = logPort;
    }

    @Override
    public UserId execute(RegisterUserCommand cmd) {
        logPort.info("Started register use-case");
        UserId userId = delegate.execute(cmd);
        logPort.info("Ended register use-case");
        return userId;
    }
}
