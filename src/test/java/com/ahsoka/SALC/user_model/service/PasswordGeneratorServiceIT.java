package com.ahsoka.SALC.user_model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PasswordGeneratorServiceIT {

    private static String prevPasswd = "";

    @RepeatedTest(3)
    public void testPasswordGenerator() {

        String password;

        PasswordGeneratorService passGen = new PasswordGeneratorService();
        password = passGen.generate();

        if (!password.equals(prevPasswd))
        {
            prevPasswd = password;

            PasswordValidatorService validPasswd = new PasswordValidatorService();
            Assertions.assertTrue(validPasswd.passwordValidate(password), "La contraseña " + password + " no es valida.");
        }
        Assertions.assertFalse(!password.equals(prevPasswd), "Las contraseñas son iguales. " + password);
    }
}