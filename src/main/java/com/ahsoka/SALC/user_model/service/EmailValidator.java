package com.ahsoka.SALC.user_model.service;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@NoArgsConstructor
public class EmailValidator implements Predicate<String> {

    Matcher matcher;
    private static final String EMAIL_REGEX = "^(.+)@salc\\.org$";
    Pattern pattern = Pattern.compile(EMAIL_REGEX);

    @Override
    public boolean test(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}