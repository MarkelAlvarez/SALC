package com.ahsoka.SALC.user_model.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class EmailValidatorTest {

    @Autowired
    EmailValidator emailValidator;

    @ParameterizedTest
    @ValueSource(strings = {"test_admin@salc.org", "test@salc.org", "userEmail@salc.org", "an.user@salc.org", "another-user@salc.org", "user1@salc.org", "userNumber1@salc.org", "user_number1@salc.org"})
    void testValidEmailTest(String email) {
        assertTrue(emailValidator.test(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test_admin", "test@hotmail.com", "notUser@.salc.org", "user@salc@org"})
    void testInvalidEmailTest(String email) {
        assertFalse(emailValidator.test(email));
    }
}
