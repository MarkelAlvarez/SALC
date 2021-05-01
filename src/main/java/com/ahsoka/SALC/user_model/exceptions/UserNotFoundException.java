package com.ahsoka.SALC.user_model.exceptions;

public class UserNotFoundException extends RuntimeException {
    private static final String DESCRIPTION = "User Not Found Exception";

    public UserNotFoundException(String detail) {
        super(DESCRIPTION + " " + detail);
    }
}
