package com.furkan.ticketport.user.model;

import com.furkan.ticketport.user.valueobject.Email;
import com.furkan.ticketport.user.valueobject.Password;
import com.furkan.ticketport.user.valueobject.Role;
import com.furkan.ticketport.user.valueobject.UserId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    @Test
    void createAssignsRoleUser() {
        User u = User.create(UserId.valueOf("id-1"), Email.valueOf("x@y.com"), Password.fromStoredHash("$2a$10$hashhashhashhashhashhashhashhashhashhash"));
        assertEquals(Role.ROLE_USER, u.role());
    }

    @Test
    void changeEmailUpdatesEmail() {
        User u = User.create(UserId.valueOf("id-1"), Email.valueOf("a@b.com"), Password.fromStoredHash("$2a$10$hashhashhashhashhashhashhashhashhashhash"));
        u.changeEmail(Email.valueOf("c@d.com"));
        assertEquals("c@d.com", u.email().asString());
    }
}
