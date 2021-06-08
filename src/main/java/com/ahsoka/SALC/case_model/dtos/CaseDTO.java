package com.ahsoka.SALC.case_model.dtos;

import com.ahsoka.SALC.case_model.persistance.entity.Case;
import com.ahsoka.SALC.case_model.persistance.entity.Court;
import com.ahsoka.SALC.case_model.persistance.entity.Jurisdiction;
import com.ahsoka.SALC.case_model.persistance.entity.Region;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
public class CaseDTO {
    @NotNull(message = "You must provide a case number!")
    @NotBlank(message = "Case number can't be blank!")
    private String caseNumber;
    @NotNull
    private Court court;
    @NotNull
    private Jurisdiction jurisdiction;
    @NotNull
    private Region region;
    @NotBlank
    @NotNull
    private String date;
    private List<String> tags;
    private String summary;

    public CaseDTO(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public CaseDTO(Case caso) {
        caseNumber = caso.getCaseNumber();
        court = caso.getCourt();
        jurisdiction = caso.getJurisdiction();
        region = caso.getRegion();
        date = caso.getDate();
        tags = caso.getTags();
        summary = caso.getSummary();
    }

    public Case toCase() {
        Case newCase = new Case();
        newCase.setCaseNumber(caseNumber);
        newCase.setRegion(region);
        newCase.setJurisdiction(jurisdiction);
        newCase.setCourt(court);
        newCase.setTags(tags);
        newCase.setSummary(summary);
        newCase.setDate(date);
        return newCase;
    }


}
