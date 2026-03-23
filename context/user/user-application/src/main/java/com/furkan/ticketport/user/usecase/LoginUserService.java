package com.furkan.ticketport.user.usecase;

import com.furkan.ticketport.user.command.LoginUserCommand;
import com.furkan.ticketport.user.exception.EmailNotFoundException;
import com.furkan.ticketport.user.exception.InvalidPasswordException;
import com.furkan.ticketport.user.model.User;
import com.furkan.ticketport.user.port.in.LoginUserUseCase;
import com.furkan.ticketport.user.port.out.HasherPort;
import com.furkan.ticketport.user.port.out.TokenIssuerPort;
import com.furkan.ticketport.user.port.out.UserQueryPort;
import com.furkan.ticketport.user.result.LoginResult;
import com.furkan.ticketport.user.valueobject.Email;
import org.springframework.stereotype.Service;

@Service
public class LoginUserService implements LoginUserUseCase {

    private final TokenIssuerPort tokenIssuerPort;
    private final UserQueryPort userQueryPort;
    private final HasherPort passwordHasher;

    public LoginUserService(TokenIssuerPort tokenIssuerPort, UserQueryPort userQueryPort, HasherPort passwordHasher) {
        this.tokenIssuerPort = tokenIssuerPort;
        this.userQueryPort = userQueryPort;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public LoginResult execute(LoginUserCommand cmd) {
        User user = findUserByEmail(cmd.email());
        if(!isPasswordTrue(user, cmd)) {
            throw new InvalidPasswordException("Password does not match");
        }
        return tokenIssuerPort.login(user);
    }

    private boolean isPasswordTrue(User user, LoginUserCommand cmd) {
        return passwordHasher.match(cmd.rawPassword().asString(), user.password().asString());
    }

    private User findUserByEmail(Email email) {
        return userQueryPort.findUserByEmail(email)
                .orElseThrow(() ->
                        new EmailNotFoundException(
                                String.format("User could not found with [%s] e-mail address", email.asString())
                        )
                );
    }
}
