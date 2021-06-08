package com.ahsoka.SALC.filter;

import com.ahsoka.SALC.user_model.persistance.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtServiceIT {

    private JwtService jwtService;

    @BeforeEach
    void before() {
        jwtService = new JwtService("secret-sign", "es-salc-ucm", 36000);
    }

    @Test
    void testCreateToken() {
        Role role = Role.ROLE_ADMIN;
        String token = jwtService.createToken("admin@salc.org", role.toString());
        assertEquals(3, token.split("\\.").length);
        assertTrue(token.length() > 30);
        assertEquals("admin@salc.org", jwtService.user(token));
        assertEquals("ROLE_ADMIN", jwtService.role(token));
    }

    @Test
    void testExtractToken() {
        assertTrue(jwtService.user("Not Bearer").isEmpty());
    }

    @Test
    void testVerify() {
        String token = jwtService.createToken("admin@salc.org", Role.ROLE_ADMIN.toString());
        assertFalse(jwtService.user(token).isEmpty());
    }
}
