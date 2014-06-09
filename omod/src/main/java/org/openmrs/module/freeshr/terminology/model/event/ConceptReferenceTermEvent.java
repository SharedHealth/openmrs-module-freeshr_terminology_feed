package org.openmrs.module.freeshr.terminology.model.event;

import org.openmrs.ConceptReferenceTerm;

import java.util.List;

import static java.util.Arrays.asList;

public class ConceptReferenceTermEvent implements ConceptEvent {

    public static final String URL = "/openmrs/ws/rest/v1/conceptreferenceterm/%s?v=full";

    @Override
    public List<String> operations() {
        return asList("saveConceptReferenceTerm");
    }

    @Override
    public String getUrl(Object[] arguments) {
        ConceptReferenceTerm term = (ConceptReferenceTerm) arguments[0];
        String conceptId = term.getUuid();
        return String.format(URL, conceptId);
    }
}
