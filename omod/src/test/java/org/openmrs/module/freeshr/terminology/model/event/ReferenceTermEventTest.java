package org.openmrs.module.freeshr.terminology.model.event;

import org.ict4h.atomfeed.server.service.Event;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.ConceptReferenceTerm;

import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class ReferenceTermEventTest {

    private static final String CONCEPT_REFERENCE_TERM_ID = "uuid";

    private ConceptReferenceTerm conceptReferenceTerm;

    @Before
    public void setup() {
        conceptReferenceTerm = new ConceptReferenceTerm();
        conceptReferenceTerm.setUuid(CONCEPT_REFERENCE_TERM_ID);
    }

    @Test
    public void shouldBeOfSameCategory() throws Exception {
        Event event = new ReferenceTermEvent().asAtomFeedEvent(new Object[]{conceptReferenceTerm});
        Event anotherEvent = new ReferenceTermEvent().asAtomFeedEvent(new Object[]{conceptReferenceTerm});
        assertEquals(event.getCategory(), anotherEvent.getCategory());
    }

    @Test
    public void shouldHaveTheSameIdAsConcept() throws Exception {
        Event event = new ReferenceTermEvent().asAtomFeedEvent(new Object[]{conceptReferenceTerm});
        assertEquals(CONCEPT_REFERENCE_TERM_ID + "", event.getUuid());
    }

    @Test
    public void shouldHaveConceptIdInTheUrl() throws Exception {
        Event event = new ReferenceTermEvent().asAtomFeedEvent(new Object[]{conceptReferenceTerm});
        assertEquals("/openmrs/ws/rest/v1/conceptreferenceterm/" + CONCEPT_REFERENCE_TERM_ID, event.getUri().getPath());
    }

    @Test
    public void shouldBeAbleToRetrieveTheEntireConceptUsingTheURL() throws Exception {
        Event event = new ReferenceTermEvent().asAtomFeedEvent(new Object[]{conceptReferenceTerm});
        assertEquals("v=custom:(uuid,display,name,conceptSource,description,code,version,retired,links)", event.getUri().getQuery());
    }

    @Test
    public void shouldHaveTheContent() throws URISyntaxException {
        Event event = new ReferenceTermEvent().asAtomFeedEvent(new Object[]{conceptReferenceTerm});
        assertEquals("/openmrs/ws/rest/v1/conceptreferenceterm/" + CONCEPT_REFERENCE_TERM_ID + "?v=custom:(uuid,display,name,conceptSource,description,code,version,retired,links)", event.getContents());
    }
}