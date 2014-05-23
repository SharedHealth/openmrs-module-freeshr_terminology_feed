package org.openmrs.module.freeshr.terminology.model.event;

import org.ict4h.atomfeed.server.service.Event;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Concept;

import static org.junit.Assert.assertEquals;

public class ConceptEntityEventTest {

    private static final String CONCEPT_ID = "uuid";

    private Concept concept;

    @Before
    public void setup() {
        concept = new Concept();
        concept.setUuid(CONCEPT_ID);
    }

    @Test
    public void shouldBeOfSameCategory() throws Exception {
        Event event = new ConceptEntityEvent().asEvent(new Object[]{concept});
        Event anotherEvent = new ConceptEntityEvent().asEvent(new Object[]{concept});
        assertEquals(event.getCategory(), anotherEvent.getCategory());
    }

    @Test
    public void shouldHaveTheSameIdAsConcept() throws Exception {
        Event event = new ConceptEntityEvent().asEvent(new Object[]{concept});
        assertEquals(CONCEPT_ID + "", event.getUuid());
    }

    @Test
    public void shouldHaveConceptIdInTheUrl() throws Exception {
        Event event = new ConceptEntityEvent().asEvent(new Object[]{concept});
        assertEquals("/openmrs/ws/rest/v1/concept/" + CONCEPT_ID, event.getUri().getPath());
    }

    @Test
    public void shouldBeAbleToRetrieveTheEntireConceptUsingTheURL() throws Exception {
        Event event = new ConceptEntityEvent().asEvent(new Object[]{concept});
        assertEquals("v=full", event.getUri().getQuery());
    }
}