package org.openmrs.module.freeshr.terminology.model.event;

import org.ict4h.atomfeed.server.service.Event;
import org.joda.time.DateTime;
import org.openmrs.ConceptReferenceTerm;

import java.net.URISyntaxException;
import java.util.List;

import static java.util.Arrays.asList;

public class ConceptReferenceTermEvent implements ConceptEvent {

    public static final String URL = "/openmrs/ws/rest/v1/conceptreferenceterm/%s?v=custom:(uuid,display,name,conceptSource,description,code,version,retired,links)";

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
        return new Event(term.getUuid(), "ConceptReferenceTerm", DateTime.now(), url, url, "ConceptReferenceTerm");
    }
}
