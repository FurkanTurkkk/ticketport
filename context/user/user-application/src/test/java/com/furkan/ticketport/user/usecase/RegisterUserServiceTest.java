package com.furkan.ticketport.user.usecase;

import com.furkan.ticketport.user.command.RegisterUserCommand;
import com.furkan.ticketport.user.exception.EmailAlreadyExistsException;
import com.furkan.ticketport.user.model.User;
import com.furkan.ticketport.user.testsupport.InMemoryUserRepository;
import com.furkan.ticketport.user.testsupport.StubHasherPort;
import com.furkan.ticketport.user.valueobject.Email;
import com.furkan.ticketport.user.valueobject.Password;
import com.furkan.ticketport.user.valueobject.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.furkan.ticketport.user.testsupport.TestPasswords.VALID_PLAIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegisterUserServiceTest {

    private InMemoryUserRepository repo;
    private RegisterUserService service;

    @BeforeEach
    void setUp() {
        repo = new InMemoryUserRepository();
        service = new RegisterUserService(repo, repo, new StubHasherPort());
    }

    @Test
    void savesNewUserAndReturnsId() {
        Email email = Email.valueOf("new@user.com");
        RegisterUserCommand cmd = new RegisterUserCommand(email, Password.valueOf(VALID_PLAIN));

        UserId id = service.execute(cmd);

        assertNotNull(id);
        assertTrue(repo.findUserByEmail(email).isPresent());
        assertEquals("HASH:" + VALID_PLAIN, repo.findUserByEmail(email).orElseThrow().password().asString());
    }

    @Test
    void rejectsDuplicateEmail() {
        Email email = Email.valueOf("dup@user.com");
        Password plain = Password.valueOf(VALID_PLAIN);
        UserId existingId = UserId.valueOf("existing-id");
        repo.save(User.create(existingId, email, Password.fromStoredHash("HASH:" + VALID_PLAIN)));

        assertThrows(
                EmailAlreadyExistsException.class,
                () -> service.execute(new RegisterUserCommand(email, plain)));
    }
}
