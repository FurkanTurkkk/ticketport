package com.furkan.ticketport.dto.request;

import com.furkan.ticketport.command.RegisterUserCommand;
import com.furkan.ticketport.valueobject.Email;
import com.furkan.ticketport.valueobject.Password;

public record RegisterUserRequest(String email, String password) {

    public RegisterUserCommand toCommand() {
        return new RegisterUserCommand(Email.valueOf(email), Password.valueOf(password));
    }
}
