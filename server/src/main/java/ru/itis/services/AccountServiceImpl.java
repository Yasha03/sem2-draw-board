package ru.itis.services;

import org.apache.commons.codec.digest.DigestUtils;
import ru.itis.models.Account;
import ru.itis.repositories.AccountRepository;
import ru.itis.validators.SignUpValidator;
import ru.itis.validators.SignUpValidatorRegex;

import java.util.Optional;

public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final SignUpValidator signUpValidator;

    public AccountServiceImpl(SignUpValidator signUpValidator, AccountRepository accountRepository) {
        this.signUpValidator = signUpValidator;
        this.accountRepository = accountRepository;
    }

    @Override
    public void signUp(Account account) {
        account.setPassword(DigestUtils.md5Hex(account.getPassword()));
        accountRepository.save(account);
    }

    @Override
    public Account loadIfExist(String email) {
        Optional<Account> account = accountRepository.findByEmail(email);
        return account.orElse(null);
    }

    @Override
    public String isCorrectAccount(Account account) {
        if (!signUpValidator.validateEmail(account.getEmail())) {
            return "Некорректный email";
        }
        if (!signUpValidator.validateLogin(account.getLogin())) {
            return "Некорректный логин";
        }
        if (!signUpValidator.validatePassword(account.getPassword())) {
            return "Некорректный пароль";
        }
        return null;
    }

    @Override
    public boolean signIn(Account account) {
        Optional<Account> accountOptional = accountRepository.findByEmail(account.getEmail());
        if (!accountOptional.isPresent()) {
            return false;
        }
        Account accountGet = accountOptional.get();
        return DigestUtils.md5Hex(account.getPassword()).equals(accountGet.getPassword());
    }
}
