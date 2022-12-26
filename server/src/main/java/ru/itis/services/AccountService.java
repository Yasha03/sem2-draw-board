package ru.itis.services;

import ru.itis.models.Account;

public interface AccountService {
    public void signUp(Account account);

    public Account loadIfExist(String email);

    public String isCorrectAccount(Account account);

    public boolean signIn(Account account);

}

