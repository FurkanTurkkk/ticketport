package com.furkan.ticketport.command;

import com.furkan.ticketport.valueobject.Email;
import com.furkan.ticketport.valueobject.Password;

public record RegisterUserCommand(Email email, Password password) {
}
