package org.openmrs.module.freeshr.terminology.model;

import org.ict4h.atomfeed.server.service.Event;
import org.openmrs.module.freeshr.terminology.model.event.ConceptEvent;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.apache.commons.collections.CollectionUtils.addIgnoreNull;
import static org.openmrs.module.freeshr.terminology.model.event.ConceptEventFactory.*;

public class ConceptOperation {

    private String name;
    private static final List<ConceptEvent> events = asList(conceptEvent(),
            diagnosisEvent(),
            findingEVent(),
            labSetEvent(),
            medSetEvent(),
            symptomEvent(),
            drugEvent(),
            testEvent(),
            referenceTermEvent());

    public ConceptOperation(Method method) {
        this.name = method.getName();
    }

    public List<Event> apply(Object[] arguments) throws Exception {
        List<Event> atomFeedEvents = new ArrayList<Event>();
        for (ConceptEvent event : events) {
            if (event.isApplicable(name)) {
                addIgnoreNull(atomFeedEvents, event.asAtomFeedEvent(arguments));
            }
        }
        return atomFeedEvents;
    }
}
