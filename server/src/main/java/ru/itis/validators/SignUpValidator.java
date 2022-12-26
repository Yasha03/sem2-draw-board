package ru.itis.validators;

public interface SignUpValidator {
    public boolean validateEmail(String email);

    public boolean validateLogin(String login);

    public boolean validatePassword(String password);

}
