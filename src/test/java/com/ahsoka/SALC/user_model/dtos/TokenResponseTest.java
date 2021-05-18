package com.ahsoka.SALC.user_model.dtos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TokenResponseTest {

    TokenResponse tokenResponse;

    @BeforeEach
    void before() {
        tokenResponse = new TokenResponse("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYmYiOjE2MjEzMTEyODAsInJvbGUiOiJST0x" +
                                          "FX0FETUlOIiwiaXNzIjoiZXMtc2FsYy11Y20iLCJleHAiOjE2MjEzNDcyODAsImlhdCI6MTYyMTMxMTI4MCwidXNlciI6ImFkbWluQHN" +
                                          "hbGMub3JnIn0.jdLHT29Y1fXhbya_5hr9NUO8OpF9OYitDHaxSQX847g");
    }

    @Test
    void testTokenResponse() {
        String expectedToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYmYiOjE2MjEzMTEyODAsInJvbGUiOiJST0x" +
                "FX0FETUlOIiwiaXNzIjoiZXMtc2FsYy11Y20iLCJleHAiOjE2MjEzNDcyODAsImlhdCI6MTYyMTMxMTI4MCwidXNlciI6ImFkbWluQHN" + "" +
                "hbGMub3JnIn0.jdLHT29Y1fXhbya_5hr9NUO8OpF9OYitDHaxSQX847g";
        assertEquals(expectedToken, tokenResponse.getToken());
    }
}
