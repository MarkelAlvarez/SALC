package com.ahsoka.SALC.user_model.service;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class PasswordValidatorService {

    public boolean passwordValidate (String password) {

        boolean general = password.matches("/^.{8,100}$/u");
        boolean uppercase = password.matches("#[A-Z]#");
        boolean lowercase = password.matches("#[a-z]#");
        boolean number = password.matches("#[0-9]#");
        boolean special = password.matches("#[-!$%^&*()_+|~=`{}\\[\\]:\";'<>?,.\\/]#");

        return general && uppercase && lowercase && number && special;
    }
}
