package com.furkan.ticketport.user.decorator;

import com.furkan.ticketport.user.command.RegisterUserCommand;
import com.furkan.ticketport.user.port.in.RegisterUserUseCase;
import com.furkan.ticketport.user.port.out.LogPort;
import com.furkan.ticketport.user.valueobject.UserId;
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
        logPort.info("useCase=RegisterUser phase=start");
        UserId userId = delegate.execute(cmd);
        logPort.info("useCase=RegisterUser phase=completed userId={}", userId.asString());
        return userId;
    }
}
