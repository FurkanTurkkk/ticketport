package com.furkan.ticketport.user.testsupport;

import com.furkan.ticketport.user.model.User;
import com.furkan.ticketport.user.port.out.UserPersistencePort;
import com.furkan.ticketport.user.port.out.UserQueryPort;
import com.furkan.ticketport.user.valueobject.Email;
import com.furkan.ticketport.user.valueobject.UserId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Register/login use case unit testleri için bellek içi “veritabanı”: email ve id ile indekslenmiş {@link Map}.
 * Gerçek JPA/Postgre yok; sadece port sözleşmesini taklit eder.
 */
public final class InMemoryUserRepository implements UserPersistencePort, UserQueryPort {

    private final Map<String, User> byEmail = new ConcurrentHashMap<>();
    private final Map<String, User> byId = new ConcurrentHashMap<>();

    @Override
    public UserId save(User user) {
        byEmail.put(user.email().asString(), user);
        byId.put(user.userId().asString(), user);
        return user.userId();
    }

    @Override
    public void delete(User user) {
        byEmail.remove(user.email().asString());
        byId.remove(user.userId().asString());
    }

    @Override
    public Optional<User> findUserById(UserId userId) {
        return Optional.ofNullable(byId.get(userId.asString()));
    }

    @Override
    public Optional<User> findUserByEmail(Email email) {
        return Optional.ofNullable(byEmail.get(email.asString()));
    }

    @Override
    public List<User> findAllUser() {
        return new ArrayList<>(byEmail.values());
    }
}
