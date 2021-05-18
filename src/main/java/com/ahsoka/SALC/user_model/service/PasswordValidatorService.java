package com.ahsoka.SALC.user_model.service;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class PasswordValidatorService {

    public boolean passwordValidate (String password) {

        return password.matches("^(?=.{8,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.+[!@#$%^&*]).*$");
    }
}
