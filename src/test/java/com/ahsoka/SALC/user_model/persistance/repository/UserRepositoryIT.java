package com.ahsoka.SALC.user_model.persistance.repository;

import com.ahsoka.SALC.user_model.UserElasticSearchContainer;
import com.ahsoka.SALC.user_model.persistance.entity.Role;
import com.ahsoka.SALC.user_model.persistance.entity.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class UserRepositoryIT {

    @Autowired
    UserRepository userRepository;

    @Container
    private static ElasticsearchContainer elasticsearchContainer = new UserElasticSearchContainer();

    @BeforeAll
    static void setUp() {
        elasticsearchContainer.start();
    }

    @AfterAll
    static void destroy() {
        elasticsearchContainer.stop();
    }

    @BeforeEach
    void before() {
        assertTrue(elasticsearchContainer.isRunning());
        fillUserIndex();
    }

    private void fillUserIndex() {
        // Admin user
        User user = new User();
        user.setEmail("test_admin@salc.org");
        user.setRole(Role.ROLE_ADMIN);
        user.setPassword("$2y$10$Ob2Hr6MF.8VYELx0lApNn.GPG/FWC.84CUXOFSBQvPxV.A5FS21Km");
        userRepository.save(user);
        // Worker user
        user = new User();
        user.setEmail("test_worker@salc.org");
        user.setRole(Role.ROLE_WORKER);
        user.setPassword("$2y$10$7o0gVbIQyE8b6nrPKqFSUeMqaHfZ7UQeInxr9AjFr4CgMcAAB4KhK");
        userRepository.save(user);
    }

    @Test
    void testFindByEmail() { assertTrue(userRepository.findByEmail("test_admin@salc.org").isPresent()); }

    @Test
    void testFindAll() { assertFalse(userRepository.findAll().isEmpty()); }

    @Test
    void testDeleteByEmail() {
        long actual = userRepository.count();
        userRepository.deleteByEmail("test_admin@salc.org");
        long afterDelete = userRepository.count();
        assertNotEquals(actual, afterDelete);
        }





}
