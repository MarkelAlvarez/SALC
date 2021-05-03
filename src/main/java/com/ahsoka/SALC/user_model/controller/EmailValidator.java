package com.ahsoka.SALC.user_model.controller;

import lombok.NoArgsConstructor;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor
public class EmailValidator implements Predicate<String> {

    Matcher matcher;
    private static final String EMAIL_REGEX = "^(.+)@salc.org";
    Pattern pattern = Pattern.compile(EMAIL_REGEX);

    @Override
    public boolean test(String email) {

        matcher = pattern.matcher(email);
        if (matcher.matches())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}