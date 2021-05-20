package com.ahsoka.SALC.user_model.dtos;

import com.ahsoka.SALC.user_model.persistance.entity.Role;
import com.ahsoka.SALC.user_model.persistance.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateUserRequest {

    private String email;
    private String password;
    private Role role;

    public User toUser(){
        User user = new User();
        user.setEmail(email);
        user.setRole(role);
        user.setPassword(password);

        return user;
    }
}