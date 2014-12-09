package org.openmrs.module.freeshr.terminology.model;

import org.ict4h.atomfeed.server.service.Event;
import org.openmrs.module.freeshr.terminology.model.event.TREvent;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.apache.commons.collections.CollectionUtils.addIgnoreNull;
import static org.openmrs.module.freeshr.terminology.model.event.TREventFactory.*;

public class Operation {

    private String name;
    private static final List<TREvent> events = asList(conceptEvent(),
            testEvent(),
            procedureEvent(),
            drugEvent(),
            diagnosisEvent(),
            findingEvent(),
            questionEvent(),
            labSetEvent(),
            medSetEvent(),
            symptomEvent(),
            symptomAndFindingEvent(),
            referenceTermEvent(),
            valueSetEvent(),
            medicationEvent());


    public Operation(Method method) {
        this.name = method.getName();
    }

    public List<Event> apply(Object[] arguments) throws Exception {
        List<Event> atomFeedEvents = new ArrayList<Event>();
        for (TREvent event : events) {
            if (event.isApplicable(name)) {
                addIgnoreNull(atomFeedEvents, event.asAtomFeedEvent(arguments));
            }
        }
        return atomFeedEvents;
    }
}
