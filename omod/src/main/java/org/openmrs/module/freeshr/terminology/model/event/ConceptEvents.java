package org.openmrs.module.freeshr.terminology.model.event;

import java.util.List;

import static java.util.Arrays.asList;

public class ConceptEvents {

    private List<ConceptEvent> conceptEvents;

    public ConceptEvents() {
        conceptEvents = asList(new ConceptEntityEvent(), new ConceptReferenceTermEvent());
    }

    public ConceptEvent findByName(String operation) {
        for (ConceptEvent conceptEvent : conceptEvents) {
            if (conceptEvent.operations().contains(operation)) {
                return conceptEvent;
            }
        }
        return null;
    }
}
