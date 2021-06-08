package com.ahsoka.SALC.case_model.persistance.repository;

import com.ahsoka.SALC.case_model.persistance.entity.Case;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

public interface CaseRepository extends ElasticsearchRepository<Case, String> {

    Optional<Case> findByCaseNumber(String caseNumber);
    List<Case> findAll();
    void deleteByCaseNumber(String caseNumber);
    @Query("{\"match_phrase\": {\"file.filename\": \"?0\"}}")
    Optional<Case> findByFileName(String fileName);
}
