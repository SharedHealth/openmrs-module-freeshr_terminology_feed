package org.openmrs.module.freeshr.terminology.model.event;

import org.openmrs.Concept;

import java.util.List;

import static java.util.Arrays.asList;

public class ConceptEntityEvent implements ConceptEvent {

    public static final String URL = "/openmrs/ws/rest/v1/concept/%s?v=full";

    @Override
    public List<String> operations() {
        return asList("saveConcept", "updateConcept");
    }

    @Override
    public String getUrl(Object[] arguments) {
        Concept term = (Concept) arguments[0];
        String conceptId = term.getUuid();
        return String.format(URL, conceptId);
    }
}
