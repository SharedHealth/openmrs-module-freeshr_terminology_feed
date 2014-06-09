package org.openmrs.module.freeshr.terminology.model.event;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.Concept;

import static org.junit.Assert.assertTrue;

public class ConceptEntityEventTest {

    private static final String CONCEPT_ID = "uuid";

    private Concept concept;

    @Before
    public void setup() {
        concept = new Concept();
        concept.setUuid(CONCEPT_ID);
    }

    @Test
    public void shouldHaveConceptIdInTheUrl() throws Exception {
        String url = new ConceptEntityEvent().getUrl(new Object[]{concept});
        assertTrue(url.contains("/openmrs/ws/rest/v1/concept/" + CONCEPT_ID));
    }

    @Test
    public void shouldBeAbleToRetrieveTheEntireConceptUsingTheURL() throws Exception {
        String url = new ConceptEntityEvent().getUrl(new Object[]{concept});
        assertTrue(url.contains("v=full"));
    }
}