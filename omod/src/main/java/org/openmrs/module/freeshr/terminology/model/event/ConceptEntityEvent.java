package org.openmrs.module.freeshr.terminology.model.event;

import org.ict4h.atomfeed.server.service.Event;
import org.joda.time.DateTime;
import org.openmrs.Concept;

import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;

public class ConceptEntityEvent implements ConceptEvent {

    private static final String TITLE = "Concept";
    private static final String CATEGORY = "Concept";
    public static final String URL = "/openmrs/ws/rest/v1/concept/%s?v=full";

    public List<String> operations() {
        return asList("saveConcept", "updateConcept");
    }

    public Event asEvent(Object[] arguments) throws URISyntaxException {
        Concept concept = (Concept) arguments[0];
        String url = String.format(URL, concept.getUuid());
        return new Event(UUID.randomUUID().toString(), TITLE, DateTime.now(), url, url, CATEGORY);
    }
}
