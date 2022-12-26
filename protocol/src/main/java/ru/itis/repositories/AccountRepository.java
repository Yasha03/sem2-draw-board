package ru.itis.repositories;

import ru.itis.models.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {
    public void save(Account account);

    public Optional<Account> findByEmail(String email);

    public Optional<Account> findById(UUID id);

    public void update(Account account);
}
