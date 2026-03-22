package com.furkan.ticketport.usecase;

import com.furkan.ticketport.command.RegisterUserCommand;
import com.furkan.ticketport.model.User;
import com.furkan.ticketport.port.in.RegisterUserUseCase;
import com.furkan.ticketport.port.out.UserPersistencePort;
import com.furkan.ticketport.valueobject.UserId;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserService implements RegisterUserUseCase {

    private final UserPersistencePort userPersistencePort;

    public RegisterUserService(UserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public UserId execute(RegisterUserCommand cmd) {
        User user = User.create(UserId.random(),
                cmd.email(),
                cmd.password());

        return userPersistencePort.save(user);
    }
}
