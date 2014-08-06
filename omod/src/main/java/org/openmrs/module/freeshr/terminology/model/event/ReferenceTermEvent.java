package org.openmrs.module.freeshr.terminology.model.event;

import org.ict4h.atomfeed.server.service.Event;
import org.joda.time.DateTime;
import org.openmrs.ConceptReferenceTerm;

import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;

public class ReferenceTermEvent implements TREvent {

    public static final String URL = "/openmrs/ws/rest/v1/tr/referenceterms/%s";

    private List<String> operations() {
        return asList("saveConceptReferenceTerm", "retireConceptReferenceTerm");
    }

    public Boolean isApplicable(String operation) {
        return this.operations().contains(operation);
    }

    public Event asAtomFeedEvent(Object[] arguments) throws URISyntaxException {
        ConceptReferenceTerm term = (ConceptReferenceTerm) arguments[0];
        String url = String.format(URL, term.getUuid());
        return new Event(UUID.randomUUID().toString(), "ConceptReferenceTerm", DateTime.now(), url, url, "ConceptReferenceTerm");
    }
}
