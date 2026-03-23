package com.furkan.ticketport.user.port.out;

import com.furkan.ticketport.user.model.User;
import com.furkan.ticketport.user.result.LoginResult;

public interface TokenIssuerPort {
    LoginResult login(User user);
}
