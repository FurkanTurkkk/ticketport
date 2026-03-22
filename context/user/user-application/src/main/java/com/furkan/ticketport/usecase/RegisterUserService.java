package com.furkan.ticketport.usecase;

import com.furkan.ticketport.command.RegisterUserCommand;
import com.furkan.ticketport.model.User;
import com.furkan.ticketport.port.in.RegisterUserUseCase;
import com.furkan.ticketport.port.out.HasherPort;
import com.furkan.ticketport.port.out.UserPersistencePort;
import com.furkan.ticketport.valueobject.Password;
import com.furkan.ticketport.valueobject.UserId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

