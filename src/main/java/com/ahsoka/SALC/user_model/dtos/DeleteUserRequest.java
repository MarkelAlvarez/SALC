package com.ahsoka.SALC.user_model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class DeleteUserRequest {

    @NotNull
    private String email;
}