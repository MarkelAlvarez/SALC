package com.ahsoka.SALC.user_model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PasswdGeneratorIT {

    @Test
    public void whenPasswordGeneratedUsingPassay_thenSuccessful() {

        String password;
        int specialCharCount = 0;

        PasswdGenerator passGen = new PasswdGenerator();
        password = passGen.generate();

        for (char c : password.toCharArray())
        {
            if (c >= 33 || c <= 47)
            {
                specialCharCount++;
            }
        }

        Assertions.assertTrue(specialCharCount >= 2, "Password validation failed in Passay");
    }
}
