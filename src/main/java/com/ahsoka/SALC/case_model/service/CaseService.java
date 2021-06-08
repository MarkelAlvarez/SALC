package com.ahsoka.SALC.case_model.service;

import com.ahsoka.SALC.case_model.dtos.CaseDTO;
import com.ahsoka.SALC.case_model.persistance.entity.Case;
import com.ahsoka.SALC.case_model.persistance.repository.CaseRepository;
import com.ahsoka.SALC.case_model.util.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


@Service
@AllArgsConstructor
public class CaseService {

    CaseRepository caseRepository;

    public Response addCase(String fileName, Case caso) {
        Optional<Case> addedCase = caseRepository.findByFileName(fileName);
        if(addedCase.isPresent()) {
            addedCase.get().setCaseNumber(caso.getCaseNumber());
            addedCase.get().setTags(caso.getTags());
            addedCase.get().setSummary(caso.getSummary());
            addedCase.get().setCourt(caso.getCourt());
            addedCase.get().setJurisdiction(caso.getJurisdiction());
            addedCase.get().setRegion(caso.getRegion());
            addedCase.get().setDate(caso.getDate());
            addedCase.get().setContent(addedCase.get().getContent());
            caseRepository.save(addedCase.get());
            return Response.OK;
        }
        return Response.CASE_NOT_FOUND;
    }

    public Stream<CaseDTO> readAll() {
        List<CaseDTO> caseStream = new ArrayList<>();
        caseRepository.findAll().forEach(caso -> caseStream.add(new CaseDTO(caso)));
        return caseStream.stream();
    }

    public Optional<CaseDTO> readCaseByCaseNumber(String caseNumber) {
        return caseRepository.findByCaseNumber(caseNumber).stream().map(CaseDTO::new).findAny();
    }

    public Response deleteCase(String caseNumber) {
        Optional<Case> referenceCase = caseRepository.findByCaseNumber(caseNumber);

        if(referenceCase.isPresent()) {
            caseRepository.deleteByCaseNumber(caseNumber);
            return Response.OK;
        }
        return Response.CASE_NOT_FOUND;
    }
}
