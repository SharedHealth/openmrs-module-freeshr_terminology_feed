package org.openmrs.module.freeshr.terminology.web.controller;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.ConceptService;
import org.openmrs.module.freeshr.terminology.utils.Constants;
import org.openmrs.module.freeshr.terminology.utils.UrlUtil;
import org.openmrs.module.freeshr.terminology.web.api.Concept;
import org.openmrs.module.freeshr.terminology.web.api.SimpleConceptRepresentation;
import org.openmrs.module.freeshr.terminology.web.api.mapper.ConceptMapper;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@org.springframework.test.context.ContextConfiguration(locations = {"classpath:TestingApplicationContext.xml"}, inheritLocations = true)
public class ConceptControllerIT extends BaseModuleWebContextSensitiveTest {
    private ConceptController conceptController;

    private ConceptService conceptService;

    @Autowired
    private ConceptMapper conceptMapper;
    @Autowired
    private UrlUtil urlUtil;

    @Before
    public void setUp() {
        conceptService = org.openmrs.api.context.Context.getConceptService();
        conceptController = new ConceptController(conceptMapper, conceptService, urlUtil);
    }

    @Test
    public void shouldMapTheConceptWithAnswerConcepts() throws Exception {
        executeDataSet("concept_with_answers.xml");
        String conceptUUID = "99110684-c97a-4a3b-9dad-5e5bf1469x0d";

        Concept concept = conceptController.getConcept(buildMockHttpRequest(conceptUUID), conceptUUID);

        assertEquals("Coded Concept with Answer as Concept", concept.getFullySpecifiedName().getConceptName());
        Set<SimpleConceptRepresentation> conceptAnswers = concept.getConceptAnswers();
        assertEquals(2, conceptAnswers.size());

        SimpleConceptRepresentation answer1 = getAnswerByUUID(conceptAnswers, "ddea03a4-c986-4bcd-b5d7-212b9fc52581");
        assertEquals("diphenhydramine", answer1.getDisplay());
        assertTrue(answer1.getUri().endsWith("/concepts/" + answer1.getUuid()));

        SimpleConceptRepresentation answer2 = getAnswerByUUID(conceptAnswers, "99110684-c97a-4a3b-9dad-5e5bf146867a");
        assertEquals("Paracetamol", answer2.getDisplay());
        assertTrue(answer2.getUri().endsWith("/concepts/" + answer2.getUuid()));
        assertTrue(CollectionUtils.isEmpty(concept.getSetMembers()));
    }

    @Test
    public void shouldMapAnswerConceptsAsConceptDrugs() throws Exception {
        executeDataSet("concept_with_answers.xml");
        String conceptUUID = "99110684-c97a-jh78-9dad-5e5bf1469x0d";

        Concept concept = conceptController.getConcept(buildMockHttpRequest(conceptUUID), conceptUUID);

        assertEquals("Coded Concept with Answer as Concept Drugs", concept.getFullySpecifiedName().getConceptName());
        Set<SimpleConceptRepresentation> conceptAnswers = concept.getConceptAnswers();
        assertEquals(2, conceptAnswers.size());
        SimpleConceptRepresentation answer1 = getAnswerByUUID(conceptAnswers, "ada47e63-5988-4f51-8282-e22fb66a7332");
        assertEquals("Crocin 500mg", answer1.getDisplay());
        assertTrue(answer1.getUri().endsWith("/drugs/" + answer1.getUuid()));
        SimpleConceptRepresentation answer2 = getAnswerByUUID(conceptAnswers, "04558a65-0e18-4fc4-bb8d-4f9a89fbec5c");
        assertEquals("Benedryl", answer2.getDisplay());
        assertTrue(answer2.getUri().endsWith("/drugs/" + answer2.getUuid()));
        assertTrue(CollectionUtils.isEmpty(concept.getSetMembers()));
    }

    private SimpleConceptRepresentation getAnswerByUUID(Set<SimpleConceptRepresentation> conceptAnswers, String uuid) {
        for (SimpleConceptRepresentation conceptAnswer : conceptAnswers) {
            if (conceptAnswer.getUuid().equals(uuid)) return conceptAnswer;
        }
        return new SimpleConceptRepresentation();
    }

    private HttpServletRequest buildMockHttpRequest(String uuid) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setScheme("http");
        request.setServerName("tr.com");
        request.setServerPort(8081);
        request.setMethod("GET");
        request.setRequestURI(Constants.REST_URL_CONCEPT + "/" + uuid);
        return request;
    }

}