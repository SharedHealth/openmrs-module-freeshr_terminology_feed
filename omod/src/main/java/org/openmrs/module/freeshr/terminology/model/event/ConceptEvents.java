package org.openmrs.module.freeshr.terminology.model.event;

import org.ict4h.atomfeed.server.service.Event;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.apache.commons.collections.CollectionUtils.addIgnoreNull;

public class ConceptEvents {

    private List<ConceptEvent> conceptEvents;

    public ConceptEvents() {
        conceptEvents = asList(new ConceptEntityEvent(),
                new ConceptReferenceTermEvent(),
                new DiagnosisEvent());
    }

    public List<Event> asEvents(String operation, Object[] arguments) throws URISyntaxException {
        List<Event> events = new ArrayList<Event>();
        for (ConceptEvent conceptEvent : conceptEvents) {
            if (conceptEvent.isApplicable(operation)) {
                addIgnoreNull(events, conceptEvent.asEvent(arguments));
            }
        }
        return events;
    }
}
