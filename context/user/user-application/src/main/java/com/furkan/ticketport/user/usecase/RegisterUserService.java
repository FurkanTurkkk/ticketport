package com.furkan.ticketport.user.usecase;

import com.furkan.ticketport.user.command.RegisterUserCommand;
import com.furkan.ticketport.user.exception.EmailAlreadyExistsException;
import com.furkan.ticketport.user.model.User;
import com.furkan.ticketport.user.port.in.RegisterUserUseCase;
import com.furkan.ticketport.user.port.out.HasherPort;
import com.furkan.ticketport.user.port.out.UserPersistencePort;
import com.furkan.ticketport.user.port.out.UserQueryPort;
import com.furkan.ticketport.user.valueobject.Email;
import com.furkan.ticketport.user.valueobject.Password;
import com.furkan.ticketport.user.valueobject.UserId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterUserService implements RegisterUserUseCase {

    private final UserPersistencePort userPersistencePort;
    private final UserQueryPort queryPort;
    private final HasherPort hasherPort;

    public RegisterUserService(UserPersistencePort userPersistencePort, UserQueryPort queryPort, HasherPort hasherPort) {
        this.userPersistencePort = userPersistencePort;
        this.queryPort = queryPort;
        this.hasherPort = hasherPort;
    }

    @Override
    public UserId execute(RegisterUserCommand cmd) {
        if(userAlreadyExists(cmd.email())) {
            throw new EmailAlreadyExistsException(String.format("[%s] e-mail address already exists",cmd.email().asString()));
        }
        String hashed = hasherPort.hash(cmd.password().asString());
        User user = User.create(
                UserId.random(),
                cmd.email(),
                Password.valueOf(hashed));
        return userPersistencePort.save(user);
    }

    private boolean userAlreadyExists(Email email) {
        Optional<User> existingUser = queryPort.findUserByEmail(email);
        return existingUser.isPresent();
    }
}

