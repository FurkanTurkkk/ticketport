package com.furkan.ticketport.user.decorator;

import com.furkan.ticketport.user.command.LoginUserCommand;
import com.furkan.ticketport.user.port.out.LogPort;
import com.furkan.ticketport.user.result.LoginResult;
import com.furkan.ticketport.user.usecase.LoginUserService;
import com.furkan.ticketport.user.valueobject.Email;
import com.furkan.ticketport.user.valueobject.Password;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.furkan.ticketport.user.testsupport.TestPasswords.VALID_PLAIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoggingLoginUserUseCaseTest {

    @Mock
    private LoginUserService delegate;

    @Mock
    private LogPort logPort;

    @Test
    void logsAroundDelegate() {
        LoggingLoginUserUseCase logging = new LoggingLoginUserUseCase(delegate, logPort);
        LoginUserCommand cmd = new LoginUserCommand(Email.valueOf("a@b.com"), Password.valueOf(VALID_PLAIN));
        LoginResult result = new LoginResult("t");
        when(delegate.execute(cmd)).thenReturn(result);

        assertEquals(result, logging.execute(cmd));

        verify(logPort).info("useCase=LoginUser phase=start (credentials redacted)");
        verify(logPort).info("useCase=LoginUser phase=completed (token redacted)");
    }
}
