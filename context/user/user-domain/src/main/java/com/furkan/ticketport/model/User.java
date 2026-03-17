package com.furkan.ticketport.model;

import com.furkan.ticketport.valueobject.Email;
import com.furkan.ticketport.valueobject.Password;
import com.furkan.ticketport.valueobject.Role;
import com.furkan.ticketport.valueobject.UserId;

import java.time.Instant;

public class User {

    private UserId userId;
    private Email email;
    private Password password;
    private Role role;
    private final Instant createdAt;
    private Instant updatedAt;

    private User(UserId userId, Email email, Password password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.role = Role.ROLE_USER;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public static User create(UserId userId, Email email, Password password) {
        return new User(userId, email, password);
    }

    public UserId userId() {return this.userId; }
    public Email email() {return this.email; }
    public Password password() {return this.password; }
    public Role role() {return this.role; }
    public Instant createdAt() {return this.createdAt; }
    public Instant updatedAt() {return this.updatedAt; }

    public void changeEmail(Email email) {
        this.email = email;
        refreshUpdatedAt();
    }

    private void refreshUpdatedAt() {
        this.updatedAt = Instant.now();
    }
}
