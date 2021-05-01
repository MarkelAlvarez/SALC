package com.ahsoka.SALC.user.persistance.repository;

import com.ahsoka.SALC.user_model.persistance.entity.User;
import com.ahsoka.SALC.user_model.persistance.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class UserRepositoryIT {

    @Autowired
    UserRepository userRepository;

    @Disabled
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
