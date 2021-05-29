package com.ahsoka.SALC.user_model.dtos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DeleteUserRequestTest {

    private DeleteUserRequest deleteUserRequest;

    @BeforeEach
    void before() {
        deleteUserRequest = new DeleteUserRequest("test@salc.org");
    }

    @Test
    void testDeleteUserRequest() {
        String expectedUserEmail = "test@salc.org";
        assertEquals(expectedUserEmail, deleteUserRequest.getEmail());
    }
}
