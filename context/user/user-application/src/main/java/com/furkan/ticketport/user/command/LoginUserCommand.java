package com.furkan.ticketport.user.command;

import com.furkan.ticketport.user.valueobject.Email;
import com.furkan.ticketport.user.valueobject.Password;

public record LoginUserCommand(Email email, Password rawPassword) {
}
