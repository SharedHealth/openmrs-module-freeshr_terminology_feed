package org.openmrs.module.freeshr.terminology.model.event;

import org.apache.commons.lang.StringUtils;
import org.ict4h.atomfeed.server.service.Event;
import org.joda.time.DateTime;
import org.openmrs.ConceptReferenceTerm;

import java.net.URISyntaxException;
import java.util.List;

import static java.util.Arrays.asList;

public class ConceptReferenceTermEvent implements ConceptEvent {

    private static final String TITLE = "ConceptReferenceTerm";
    private static final String CATEGORY = "ConceptReferenceTerm";
    public static final String URL = "/openmrs/ws/rest/v1/conceptreferenceterm/%s?v=full";

    public List<String> operations() {
        return asList("saveConceptReferenceTerm");
    }

    public Event asEvent(Object[] arguments) throws URISyntaxException {
        ConceptReferenceTerm term = (ConceptReferenceTerm) arguments[0];
        String conceptId = term.getUuid();
        String url = String.format(URL, conceptId);
        return new Event(term.getUuid(), TITLE, DateTime.now(), url, url, CATEGORY);
    }
}
