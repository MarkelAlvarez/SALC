package com.ahsoka.SALC.user_model.dtos;

import lombok.Data;
import javax.validation.constraints.NotNull;


@Data

public class DeleteUserRequest {

    @NotNull
    private String email;

}