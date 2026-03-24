package com.furkan.ticketport.user.decorator;

import com.furkan.ticketport.user.command.RegisterUserCommand;
import com.furkan.ticketport.user.port.out.LogPort;
import com.furkan.ticketport.user.valueobject.Email;
import com.furkan.ticketport.user.valueobject.Password;
import com.furkan.ticketport.user.valueobject.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.furkan.ticketport.user.testsupport.TestPasswords.VALID_PLAIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoggingRegisterUserUseCaseTest {

    @Mock
    private TransactionalRegisterUserUseCase delegate;

    @Mock
    private LogPort logPort;

    @Test
    void logsAroundDelegate() {
        LoggingRegisterUserUseCase logging =
                new LoggingRegisterUserUseCase(delegate, logPort);
        RegisterUserCommand cmd =
                new RegisterUserCommand(Email.valueOf("a@b.com"), Password.valueOf(VALID_PLAIN));
        UserId id = UserId.valueOf("u-1");
        when(delegate.execute(cmd)).thenReturn(id);

        assertEquals(id, logging.execute(cmd));

        verify(logPort).info("Started register use-case");
        verify(logPort).info("Ended register use-case");
    }
}
