package org.openmrs.module.freeshr.terminology.model;

import org.ict4h.atomfeed.server.service.Event;
import org.openmrs.module.freeshr.terminology.model.event.ConceptEvents;

import java.lang.reflect.Method;
import java.util.List;

public class ConceptOperation {

    private String name;
    private ConceptEvents conceptEvents;

    public ConceptOperation(Method method) {
        this.name = method.getName();
        this.conceptEvents = new ConceptEvents();
    }

    public List<Event> apply(Object[] arguments) throws Exception {
        return conceptEvents.asEvents(name, arguments);
    }
}
