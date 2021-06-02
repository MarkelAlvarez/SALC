package com.ahsoka.SALC.user_model.util;

import com.ahsoka.SALC.user_model.dtos.NewUserRequest;
import com.ahsoka.SALC.user_model.exceptions.CSVParseException;
import com.ahsoka.SALC.user_model.persistance.entity.Role;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
public class HandlerCSV {

    public static final String TYPE = "text/csv";

    public boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public List<NewUserRequest> csvToUsers(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<NewUserRequest> userResponses = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                NewUserRequest userRequest = new NewUserRequest(csvRecord.get("email"), Role.valueOf(csvRecord.get("role")));
                userResponses.add(userRequest);
            }

            return userResponses;
        } catch (IOException e) {
            throw new CSVParseException(e.getMessage());
        }
    }

}