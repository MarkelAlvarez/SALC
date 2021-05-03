package com.ahsoka.SALC.user_model.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class EmailValidatorTest {

    @Autowired
    EmailValidator emailValidator;

    @Test
    void testEmailValidator() {
        assertTrue(emailValidator.test("test_admin@salc.org"));
    }
}
