package com.ahsoka.SALC.user_model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PasswordGeneratorServiceIT {

    private PasswordGeneratorService passGen;
    private static String prevPassword = "";

    @BeforeEach
    void before() {
        passGen = new PasswordGeneratorService();
    }

    @RepeatedTest(10)
    void testPasswordGenerator() {
        String password = passGen.generate();

        Assertions.assertNotEquals(prevPassword, password);

        prevPassword = password;
    }
}