package ru.itis.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpValidatorRegex implements SignUpValidator {

    private static final String LOGIN_REGEX = "^[a-zA-Z\\-_\\d\\s].{3,25}$";
    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_REGEX = "^[!?.,a-zA-Z\\d-_$].{5,20}$";

    @Override
    public boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public boolean validateLogin(String nickname) {
        Pattern pattern = Pattern.compile(LOGIN_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(nickname);
        return matcher.matches();
    }

    @Override
    public boolean validatePassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
