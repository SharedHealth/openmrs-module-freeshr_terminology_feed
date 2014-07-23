package org.openmrs.module.freeshr.terminology.model;

import org.ict4h.atomfeed.server.service.Event;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Concept;
import org.openmrs.api.ConceptService;

import static org.junit.Assert.assertFalse;
import static org.openmrs.module.freeshr.terminology.utils.Lambda.first;

public class OperationTest {

    private static final String CONCEPT_ID = "uuid";

    private Concept concept;

    @Before
    public void setup() {
        concept = new Concept();
        concept.setUuid(CONCEPT_ID);
    }

    @Test
    public void shouldHaveUniqueUUIDForEachEvent() throws Exception {
        Event event = first(new Operation(ConceptService.class.getMethod("saveConcept", Concept.class)).apply(new Object[]{concept}));
        Event anotherEvent = first(new Operation(ConceptService.class.getMethod("saveConcept", Concept.class)).apply(new Object[]{concept}));
        assertFalse(event.getUuid().equals(anotherEvent.getUuid()));
    }

    @Test
    public void shouldHaveTheSameContentAsTheUrl() throws Exception {
        Event event = first(new Operation(ConceptService.class.getMethod("saveConcept", Concept.class)).apply(new Object[]{concept}));
        Event anotherEvent = first(new Operation(ConceptService.class.getMethod("saveConcept", Concept.class)).apply(new Object[]{concept}));
        assertFalse(event.getUri().getPath().equals(anotherEvent.getContents()));
    }

}