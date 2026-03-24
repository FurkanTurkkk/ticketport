package com.furkan.ticketport.user.transaction;

import com.furkan.ticketport.user.command.RegisterUserCommand;
import com.furkan.ticketport.user.usecase.RegisterUserService;
import com.furkan.ticketport.user.valueobject.Email;
import com.furkan.ticketport.user.valueobject.Password;
import com.furkan.ticketport.user.valueobject.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.furkan.ticketport.user.testsupport.TestPasswords.VALID_PLAIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterUserTransactionRunnerTest {

    @Mock
    private RegisterUserService registerUserService;

    @InjectMocks
    private RegisterUserTransactionRunner runner;

    @Test
    void delegatesToRegisterUserService() {
        RegisterUserCommand cmd =
                new RegisterUserCommand(Email.valueOf("a@b.com"), Password.valueOf(VALID_PLAIN));
        UserId id = UserId.valueOf("id-1");
        when(registerUserService.execute(cmd)).thenReturn(id);

        assertEquals(id, runner.register(cmd));

        verify(registerUserService).execute(cmd);
    }
}
