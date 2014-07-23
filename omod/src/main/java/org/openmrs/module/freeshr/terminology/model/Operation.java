package org.openmrs.module.freeshr.terminology.model;

import org.ict4h.atomfeed.server.service.Event;
import org.openmrs.module.freeshr.terminology.model.event.TREvent;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.apache.commons.collections.CollectionUtils.addIgnoreNull;
import static org.openmrs.module.freeshr.terminology.model.event.TREventFactory.conceptEvent;
import static org.openmrs.module.freeshr.terminology.model.event.TREventFactory.diagnosisEvent;
import static org.openmrs.module.freeshr.terminology.model.event.TREventFactory.drugEvent;
import static org.openmrs.module.freeshr.terminology.model.event.TREventFactory.findingEvent;
import static org.openmrs.module.freeshr.terminology.model.event.TREventFactory.labSetEvent;
import static org.openmrs.module.freeshr.terminology.model.event.TREventFactory.medSetEvent;
import static org.openmrs.module.freeshr.terminology.model.event.TREventFactory.procedureEvent;
import static org.openmrs.module.freeshr.terminology.model.event.TREventFactory.questionEvent;
import static org.openmrs.module.freeshr.terminology.model.event.TREventFactory.referenceTermEvent;
import static org.openmrs.module.freeshr.terminology.model.event.TREventFactory.symptomAndFindingEvent;
import static org.openmrs.module.freeshr.terminology.model.event.TREventFactory.symptomEvent;
import static org.openmrs.module.freeshr.terminology.model.event.TREventFactory.testEvent;

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
            referenceTermEvent());


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
