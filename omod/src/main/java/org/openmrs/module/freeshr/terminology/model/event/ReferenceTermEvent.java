package org.openmrs.module.freeshr.terminology.model.event;

import org.ict4h.atomfeed.server.service.Event;
import org.joda.time.DateTime;
import org.openmrs.ConceptReferenceTerm;

import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;

public class ReferenceTermEvent implements TREvent {

    public static final String URL = "/openmrs/ws/rest/v1/conceptreferenceterm/%s?v=custom:(uuid,name,conceptSource,description,code,version,retired,conceptReferenceTermMaps)";

    private List<String> operations() {
        return asList("saveConceptReferenceTerm");
    }

    public Boolean isApplicable(String operation) {
        return this.operations().contains(operation);
    }

    public Event asAtomFeedEvent(Object[] arguments) throws URISyntaxException {
        ConceptReferenceTerm term = (ConceptReferenceTerm) arguments[0];
        String conceptId = term.getUuid();
        String url = String.format(URL, conceptId);
        return new Event(UUID.randomUUID().toString(), "ConceptReferenceTerm", DateTime.now(), url, url, "ConceptReferenceTerm");
    }
}
