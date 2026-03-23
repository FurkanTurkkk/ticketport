package com.furkan.ticketport.user.dto.request;

import com.furkan.ticketport.user.command.LoginUserCommand;
import com.furkan.ticketport.user.valueobject.Email;
import com.furkan.ticketport.user.valueobject.Password;

public record LoginUserRequest(String email, String rawPassword) {
    public LoginUserCommand toCommand() {
        return new LoginUserCommand(Email.valueOf(this.email), Password.valueOf(this.rawPassword));
    }
}
