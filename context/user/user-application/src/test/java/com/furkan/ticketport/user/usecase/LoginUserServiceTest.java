package com.furkan.ticketport.user.usecase;

import com.furkan.ticketport.user.command.LoginUserCommand;
import com.furkan.ticketport.user.exception.EmailNotFoundException;
import com.furkan.ticketport.user.exception.InvalidPasswordException;
import com.furkan.ticketport.user.model.User;
import com.furkan.ticketport.user.result.LoginResult;
import com.furkan.ticketport.user.testsupport.FixedTokenIssuerPort;
import com.furkan.ticketport.user.testsupport.InMemoryUserRepository;
import com.furkan.ticketport.user.testsupport.StubHasherPort;
import com.furkan.ticketport.user.valueobject.Email;
import com.furkan.ticketport.user.valueobject.Password;
import com.furkan.ticketport.user.valueobject.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.furkan.ticketport.user.testsupport.TestPasswords.VALID_PLAIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoginUserServiceTest {

    private InMemoryUserRepository repo;
    private LoginUserService service;

    @BeforeEach
    void setUp() {
        repo = new InMemoryUserRepository();
        service = new LoginUserService(new FixedTokenIssuerPort("jwt-token"), repo, new StubHasherPort());
    }

    @Test
    void returnsTokenWhenPasswordMatches() {
        Email email = Email.valueOf("login@user.com");
        repo.save(
                User.create(
                        UserId.valueOf("u-1"),
                        email,
                        Password.fromStoredHash("HASH:" + VALID_PLAIN)));

        LoginResult result =
                service.execute(new LoginUserCommand(email, Password.valueOf(VALID_PLAIN)));

        assertEquals("jwt-token", result.token());
    }

    @Test
    void throwsWhenEmailUnknown() {
        assertThrows(
                EmailNotFoundException.class,
                () ->
                        service.execute(
                                new LoginUserCommand(
                                        Email.valueOf("ghost@user.com"), Password.valueOf(VALID_PLAIN))));
    }

    @Test
    void throwsWhenPasswordWrong() {
        Email email = Email.valueOf("login@user.com");
        repo.save(
                User.create(
                        UserId.valueOf("u-1"),
                        email,
                        Password.fromStoredHash("HASH:" + VALID_PLAIN)));

        assertThrows(
                InvalidPasswordException.class,
                () ->
                        service.execute(
                                new LoginUserCommand(email, Password.valueOf("WrongP1!"))));
    }
}
