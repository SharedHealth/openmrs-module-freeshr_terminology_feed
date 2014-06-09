package org.openmrs.module.freeshr.terminology.model.event;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.ConceptReferenceTerm;

import static org.junit.Assert.assertTrue;

public class ConceptReferenceTermEventTest {

    private static final String CONCEPT_REFERENCE_TERM_ID = "uuid";

    private ConceptReferenceTerm conceptReferenceTerm;

    @Before
    public void setup() {
        conceptReferenceTerm = new ConceptReferenceTerm();
        conceptReferenceTerm.setUuid(CONCEPT_REFERENCE_TERM_ID);
    }

    @Test
    public void shouldHaveConceptIdInTheUrl() throws Exception {
        String url = new ConceptReferenceTermEvent().getUrl(new Object[]{conceptReferenceTerm});
        assertTrue(url.contains("/openmrs/ws/rest/v1/conceptreferenceterm/" + CONCEPT_REFERENCE_TERM_ID));
    }

    @Test
    public void shouldBeAbleToRetrieveTheEntireConceptUsingTheURL() throws Exception {
        String url = new ConceptReferenceTermEvent().getUrl(new Object[]{conceptReferenceTerm});
        assertTrue(url.contains("v=full"));
    }
}