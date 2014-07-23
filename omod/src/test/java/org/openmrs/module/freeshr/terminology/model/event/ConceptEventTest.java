package org.openmrs.module.freeshr.terminology.model.event;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.openmrs.module.freeshr.terminology.model.event.TREventFactory.conceptEvent;

public class ConceptEventTest {

    @Test
    public void shouldIdentifyIfAnOperationIsApplicable() throws Exception {
        assertTrue(conceptEvent().isApplicable("saveConcept"));
        assertTrue(conceptEvent().isApplicable("updateConcept"));
        assertFalse(conceptEvent().isApplicable("unknown"));
    }

}