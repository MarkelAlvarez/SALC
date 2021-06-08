package com.ahsoka.SALC.case_model.persistance.repository;

import com.ahsoka.SALC.CustomElasticSearchContainer;
import com.ahsoka.SALC.case_model.persistance.entity.Case;
import com.ahsoka.SALC.case_model.persistance.entity.Court;
import com.ahsoka.SALC.case_model.persistance.entity.Jurisdiction;
import com.ahsoka.SALC.case_model.persistance.entity.Region;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class CaseRepositoryIT {

    @Autowired
    CaseRepository caseRepository;


    @Container
    private static ElasticsearchContainer elasticsearchContainer = new CustomElasticSearchContainer();

    @BeforeAll
    static void setUp() {
        elasticsearchContainer.start();
    }

    @AfterAll
    static void destroy() {
        elasticsearchContainer.stop();
    }


    @BeforeEach
    void before() {
        assertTrue(elasticsearchContainer.isRunning());
        fillCaseIndex();
    }

    private void fillCaseIndex() {
        Case newCase = new Case();
        newCase.setCaseNumber("20907/2017");
        newCase.setCourt(Court.SUPREMO);
        newCase.setJurisdiction(Jurisdiction.PENAL);
        newCase.setRegion(Region.MADRID);
        newCase.setDate("14/10/2019");
        List<String> tags = new ArrayList<>();
        tags.add("proces");
        newCase.setTags(tags);
        caseRepository.save(newCase);
    }

    @Test
    void testFindAll() {
        assertFalse(caseRepository.findAll().isEmpty());
        List<Case> cases = caseRepository.findAll();
        for(Case aCase : cases)
            System.out.println(aCase);
    }
}
