package com.furkan.ticketport.user.usecase;

import com.furkan.ticketport.user.command.RegisterUserCommand;
import com.furkan.ticketport.user.model.User;
import com.furkan.ticketport.user.port.in.RegisterUserUseCase;
import com.furkan.ticketport.user.port.out.HasherPort;
import com.furkan.ticketport.user.port.out.UserPersistencePort;
import com.furkan.ticketport.user.valueobject.Password;
import com.furkan.ticketport.user.valueobject.UserId;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserService implements RegisterUserUseCase {

    private final UserPersistencePort userPersistencePort;
    private final HasherPort hasherPort;

    public RegisterUserService(UserPersistencePort userPersistencePort, HasherPort hasherPort) {
        this.userPersistencePort = userPersistencePort;
        this.hasherPort = hasherPort;
    }

    @Override
    public UserId execute(RegisterUserCommand cmd) {
        String hashed = hasherPort.hash(cmd.password().asString());
        User user = User.create(
                UserId.random(),
                cmd.email(),
                Password.valueOf(hashed));
        return userPersistencePort.save(user);
    }
}

