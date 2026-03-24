package com.furkan.ticketport.user.decorator;

import com.furkan.ticketport.user.command.RegisterUserCommand;
import com.furkan.ticketport.user.exception.EmailAlreadyExistsException;
import com.furkan.ticketport.user.transaction.RegisterUserTransactionRunner;
import com.furkan.ticketport.user.valueobject.Email;
import com.furkan.ticketport.user.valueobject.Password;
import com.furkan.ticketport.user.valueobject.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import static com.furkan.ticketport.user.testsupport.TestPasswords.VALID_PLAIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionalRegisterUserUseCaseTest {

    @Mock
    private RegisterUserTransactionRunner transactionRunner;

    @InjectMocks
    private TransactionalRegisterUserUseCase useCase;

    @Test
    void mapsDataIntegrityViolationToEmailAlreadyExists() {
        RegisterUserCommand cmd =
                new RegisterUserCommand(Email.valueOf("x@y.com"), Password.valueOf(VALID_PLAIN));
        when(transactionRunner.register(cmd)).thenThrow(new DataIntegrityViolationException("dup"));

        assertThrows(EmailAlreadyExistsException.class, () -> useCase.execute(cmd));
    }

    @Test
    void delegatesSuccess() {
        RegisterUserCommand cmd =
                new RegisterUserCommand(Email.valueOf("ok@y.com"), Password.valueOf(VALID_PLAIN));
        UserId id = UserId.valueOf("id-1");
        when(transactionRunner.register(cmd)).thenReturn(id);

        assertEquals(id, useCase.execute(cmd));
    }
}
