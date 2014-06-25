package org.openmrs.module.freeshr.terminology.model.event;

import org.ict4h.atomfeed.server.service.Event;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Concept;

import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.openmrs.module.freeshr.terminology.utils.Lambda.first;

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
    public void twoEventsForSameConceptShouldNotHaveTheSameUUID() throws Exception {
        Event event1 = new ConceptEntityEvent().asEvent(new Object[]{concept});
        Event event2 = new ConceptEntityEvent().asEvent(new Object[]{concept});
        assertFalse(event1.getUuid().equals(event2.getUuid()));
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

    @Test
    public void shouldHaveConcenptContent() throws URISyntaxException {
        Event event = new ConceptEntityEvent().asEvent(new Object[]{concept});
        assertEquals("/openmrs/ws/rest/v1/concept/" + CONCEPT_ID + "?v=full", event.getContents());
    }
}