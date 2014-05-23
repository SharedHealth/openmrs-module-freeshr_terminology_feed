package org.openmrs.module.freeshr.terminology.model.event;

import org.ict4h.atomfeed.server.service.Event;

import java.net.URISyntaxException;
import java.util.List;

public interface ConceptEvent {

    public List<String> operations();

    public Event asEvent(Object[] argument) throws URISyntaxException;

}
