package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class RegisterForm {
    private String login;

    private String email;

    private String password;

    public Account collectAccount(){
        return Account.builder()
                .login(login)
                .email(email)
                .password(password)
                .build();
    }
}
