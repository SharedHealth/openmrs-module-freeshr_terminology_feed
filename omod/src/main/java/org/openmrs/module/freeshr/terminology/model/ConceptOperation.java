package org.openmrs.module.freeshr.terminology.model;

import org.ict4h.atomfeed.server.service.Event;
import org.openmrs.module.freeshr.terminology.model.event.ConceptEvent;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.apache.commons.collections.CollectionUtils.addIgnoreNull;
import static org.openmrs.module.freeshr.terminology.model.event.ConceptEventFactory.conceptEvent;
import static org.openmrs.module.freeshr.terminology.model.event.ConceptEventFactory.diagnosisEvent;
import static org.openmrs.module.freeshr.terminology.model.event.ConceptEventFactory.drugEvent;
import static org.openmrs.module.freeshr.terminology.model.event.ConceptEventFactory.findingEvent;
import static org.openmrs.module.freeshr.terminology.model.event.ConceptEventFactory.labSetEvent;
import static org.openmrs.module.freeshr.terminology.model.event.ConceptEventFactory.medSetEvent;
import static org.openmrs.module.freeshr.terminology.model.event.ConceptEventFactory.procedureEvent;
import static org.openmrs.module.freeshr.terminology.model.event.ConceptEventFactory.questionEvent;
import static org.openmrs.module.freeshr.terminology.model.event.ConceptEventFactory.referenceTermEvent;
import static org.openmrs.module.freeshr.terminology.model.event.ConceptEventFactory.symptomAndFindingEvent;
import static org.openmrs.module.freeshr.terminology.model.event.ConceptEventFactory.symptomEvent;
import static org.openmrs.module.freeshr.terminology.model.event.ConceptEventFactory.testEvent;

public class ConceptOperation {

    private String name;
    private static final List<ConceptEvent> events = asList(conceptEvent(),
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
