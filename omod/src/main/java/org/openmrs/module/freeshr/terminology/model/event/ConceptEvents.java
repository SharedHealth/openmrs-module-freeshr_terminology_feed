package org.openmrs.module.freeshr.terminology.model.event;

import org.ict4h.atomfeed.server.service.Event;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class ConceptEvents {

    private List<ConceptEvent> conceptEvents;

    public ConceptEvents() {
        conceptEvents = asList(new ConceptEntityEvent(), new ConceptReferenceTermEvent());
    }

    public List<String> allConceptOperations() {
        List<String> allOperations = new ArrayList<>();
        for (ConceptEvent conceptEvent : conceptEvents) {
            allOperations.addAll(conceptEvent.operations());
        }
        return allOperations;
    }

    public Event asEvent(String operation, Object[] arguments) throws URISyntaxException {
        for (ConceptEvent conceptEvent : conceptEvents) {
            if (conceptEvent.operations().contains(operation)) {
                return conceptEvent.asEvent(arguments);
            }
        }
        return null;
    }
}
