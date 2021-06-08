package com.ahsoka.SALC.user_model.exceptions;

public class CSVParseException extends RuntimeException {
    private static final String DESCRIPTION = "Fail to parse CSV file:";

    public CSVParseException(String detail) {
        super(DESCRIPTION + " " + detail);
    }
}
