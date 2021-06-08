package com.ahsoka.SALC.case_model.controller;

import com.ahsoka.SALC.case_model.dtos.CaseDTO;
import com.ahsoka.SALC.case_model.service.CaseService;
import com.ahsoka.SALC.case_model.util.Response;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@AllArgsConstructor
@NoArgsConstructor
public class CaseController {

    @Autowired
    private CaseService caseService;
    private static final String CASES = "/cases/";

    @PreAuthorize("hasRole('ROLE_CATALOGER')")
    @PostMapping(value = CASES)
    public String addCase(@RequestParam String fileName, @Valid @RequestBody CaseDTO caseDTO) {
        Response response = caseService.addCase(fileName, caseDTO.toCase());

        if (response.equals(Response.CASE_NOT_FOUND))
            return HttpServletResponse.SC_BAD_REQUEST + " " + response;
        else
            return HttpServletResponse.SC_OK + " " + response;
    }

    @PreAuthorize("hasAnyRole('ROLE_WORKER', 'ROLE_CATALOGER')")
    @GetMapping(value = CASES)
    public Stream<CaseDTO> readAll() {
        return caseService.readAll();
    }

    @PreAuthorize("hasAnyRole('ROLE_WORKER', 'ROLE_CATALOGER')")
    @GetMapping(value = CASES + "caseNumber/")
    public Optional<CaseDTO> readCaseByCaseNumber(@RequestParam String caseNumber) {
        return caseService.readCaseByCaseNumber(caseNumber);
    }

    @PreAuthorize("hasRole('ROLE_CATALOGER')")
    @DeleteMapping(value = CASES)
    public String deleteCase(@RequestParam String caseNumber) {
        Response response = caseService.deleteCase(caseNumber);

        if(response.equals(Response.OK))
            return String.valueOf(HttpServletResponse.SC_OK);
        else
            return HttpServletResponse.SC_NO_CONTENT + " " + response;
    }

}
