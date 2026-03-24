package com.furkan.ticketport.user.adapter;

import com.furkan.ticketport.user.model.User;
import com.furkan.ticketport.user.result.LoginResult;
import com.furkan.ticketport.user.valueobject.Email;
import com.furkan.ticketport.user.valueobject.Password;
import com.furkan.ticketport.user.valueobject.UserId;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;

class LoginAdapterTest {

    @Test
    void loginReturnsCompactJwt() {
        LoginAdapter adapter = new LoginAdapter();
        ReflectionTestUtils.setField(
                adapter,
                "secret",
                "0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef");
        ReflectionTestUtils.setField(adapter, "expirationMs", 3_600_000L);

        User user =
                User.create(
                        UserId.valueOf("id-1"),
                        Email.valueOf("jwt@user.com"),
                        Password.fromStoredHash("$2a$10$xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"));

        LoginResult result = adapter.login(user);

        assertFalse(result.token().isBlank());
        assertFalse(result.token().contains(" "));
    }
}
