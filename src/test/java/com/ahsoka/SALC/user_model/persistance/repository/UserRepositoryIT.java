package com.ahsoka.SALC.user_model.persistance.repository;

import com.ahsoka.SALC.user_model.persistance.entity.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserRepositoryIT {

    @Autowired
    UserRepository userRepository;


    @Test
    void testFindByEmail() {
        assertTrue(userRepository.findByEmail("test_admin@salc.org").isPresent());
        System.out.println(userRepository.findByEmail("test_admin@salc.org"));
    }

    @Disabled
    @Test
    void testFindAll() {
        assertFalse(userRepository.findAll().isEmpty());
        List<User> userList = userRepository.findAll();
        for(User user : userList)
            System.out.println(user);
    }
}
