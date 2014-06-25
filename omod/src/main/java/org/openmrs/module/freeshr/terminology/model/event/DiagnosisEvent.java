package org.openmrs.module.freeshr.terminology.model.event;


import org.ict4h.atomfeed.server.service.Event;
import org.joda.time.DateTime;
import org.openmrs.Concept;

import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;

public class DiagnosisEvent implements ConceptEvent{

    public static final String URL = "/openmrs/ws/rest/v1/concept/%s?v=custom:(uuid,name:(uuid,name,locale,conceptNameType)," +
            "datatype,set,version,retired,names,answers,setMembers,mappings:(uuid,conceptReferenceTerm,conceptMapType))";

    private List<String> operations() {
        return asList("saveConcept", "updateConcept");
    }

    public Boolean isApplicable(String operation) {
        return this.operations().contains(operation);
    }

    private boolean isDiagnosis(Concept concept) {
        return concept.getConceptClass() != null && concept.getConceptClass().getName().equals("Diagnosis");
    }

    @Override
    public Event asEvent(Object[] arguments) throws URISyntaxException {
        Concept concept = (Concept) arguments[0];
        if (isDiagnosis(concept)){
            String url = String.format(URL, concept.getUuid());
            return new Event(UUID.randomUUID().toString(), "Diagnosis", DateTime.now(), url, url, "Diagnosis");
        }
        return null;
    }
}
