package com.furkan.ticketport.user.dto.request;

import com.furkan.ticketport.user.command.RegisterUserCommand;
import com.furkan.ticketport.user.valueobject.Email;
import com.furkan.ticketport.user.valueobject.Password;

public record RegisterUserRequest(String email, String password) {

    public RegisterUserCommand toCommand() {
        return new RegisterUserCommand(Email.valueOf(email), Password.valueOf(password));
    }
}
