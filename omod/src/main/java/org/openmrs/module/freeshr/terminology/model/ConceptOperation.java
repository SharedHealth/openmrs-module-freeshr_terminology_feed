package org.openmrs.module.freeshr.terminology.model;

import org.ict4h.atomfeed.server.service.Event;
import org.joda.time.DateTime;
import org.openmrs.module.freeshr.terminology.model.event.ConceptEvents;

import java.lang.reflect.Method;
import java.util.UUID;

public class ConceptOperation {

    private String name;
    private ConceptEvents conceptEvents;

    public ConceptOperation(Method method) {
        this.name = method.getName();
        this.conceptEvents = new ConceptEvents();
    }

    public boolean shouldPublishEventToFeed() {
        return null != conceptEvents.findByName(name);
    }

    public Event asEvent(Object[] arguments) throws Exception {
        String url = conceptEvents.findByName(name).getUrl(arguments);
        return new Event(UUID.randomUUID().toString(), "Concept", DateTime.now(), url, url, "Concept");
    }
}
