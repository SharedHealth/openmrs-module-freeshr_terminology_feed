package org.openmrs.module.freeshr.terminology.model.event;

import org.ict4h.atomfeed.server.service.Event;
import org.joda.time.DateTime;
import org.openmrs.ConceptReferenceTerm;
import org.openmrs.module.freeshr.terminology.utils.Constants;

import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;

public class ReferenceTermEvent implements TREvent {

    public static final String REF_TERM_PATH = Constants.WS_CONTEXT + Constants.REST_URL_REF_TERM + "/";

    private List<String> operations() {
        return asList("saveConceptReferenceTerm", "retireConceptReferenceTerm", "unretireConceptReferenceTerm");
    }

    public Boolean isApplicable(String operation) {
        return this.operations().contains(operation);
    }

    public Event asAtomFeedEvent(Object[] arguments) throws URISyntaxException {
        ConceptReferenceTerm term = (ConceptReferenceTerm) arguments[0];
        String refTermUrl = REF_TERM_PATH + term.getUuid();
        return new Event(UUID.randomUUID().toString(), "ConceptReferenceTerm", DateTime.now(), refTermUrl, refTermUrl, "ConceptReferenceTerm");
    }
}
