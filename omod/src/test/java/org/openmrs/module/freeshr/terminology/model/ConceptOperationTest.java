package org.openmrs.module.freeshr.terminology.model;

import org.ict4h.atomfeed.server.service.Event;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Concept;
import org.openmrs.api.ConceptService;

import static org.junit.Assert.assertFalse;

public class ConceptOperationTest {

    private static final String CONCEPT_ID = "uuid";

    private Concept concept;

    @Before
    public void setup() {
        concept = new Concept();
        concept.setUuid(CONCEPT_ID);
    }

    @Test
    public void shouldHaveUniqueUUIDForEachEvent() throws Exception {
        Event event = new ConceptOperation(ConceptService.class.getMethod("saveConcept", Concept.class)).asEvent(new Object[]{concept});
        Event anotherEvent = new ConceptOperation(ConceptService.class.getMethod("saveConcept", Concept.class)).asEvent(new Object[]{concept});
        assertFalse(event.getUuid().equals(anotherEvent.getUuid()));
    }

    @Test
    public void shouldHaveTheSameContentAsTheUrl() throws Exception {
        Event event = new ConceptOperation(ConceptService.class.getMethod("saveConcept", Concept.class)).asEvent(new Object[]{concept});
        Event anotherEvent = new ConceptOperation(ConceptService.class.getMethod("saveConcept", Concept.class)).asEvent(new Object[]{concept});
        assertFalse(event.getUri().getPath().equals(anotherEvent.getContents()));
    }

}