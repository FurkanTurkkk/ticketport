package com.furkan.ticketport.user.decorator;

import com.furkan.ticketport.user.command.LoginUserCommand;
import com.furkan.ticketport.user.port.in.LoginUserUseCase;
import com.furkan.ticketport.user.port.out.LogPort;
import com.furkan.ticketport.user.result.LoginResult;
import com.furkan.ticketport.user.usecase.LoginUserService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class LoggingLoginUserUseCase implements LoginUserUseCase {

    private final LoginUserService delegate;
    private final LogPort logPort;

    public LoggingLoginUserUseCase(LoginUserService delegate, LogPort logPort) {
        this.delegate = delegate;
        this.logPort = logPort;
    }

    @Override
    public LoginResult execute(LoginUserCommand cmd) {
        logPort.info("useCase=LoginUser phase=start (credentials redacted)");
        LoginResult result = delegate.execute(cmd);
        logPort.info("useCase=LoginUser phase=completed (token redacted)");
        return result;
    }
}
