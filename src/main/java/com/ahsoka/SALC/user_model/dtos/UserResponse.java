package com.ahsoka.SALC.user_model.dtos;

import com.ahsoka.SALC.user_model.persistance.entity.Role;
import com.ahsoka.SALC.user_model.persistance.entity.User;
import lombok.Data;

@Data
public class UserResponse {
    private String email;
    private Role role;
    private String password;

    public UserResponse(User user) {
        email = user.getEmail();
        role = user.getRole();
        password = user.getPassword();
    }
}
