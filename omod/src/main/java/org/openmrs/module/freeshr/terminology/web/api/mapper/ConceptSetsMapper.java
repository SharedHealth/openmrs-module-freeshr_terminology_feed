package org.openmrs.module.freeshr.terminology.web.api.mapper;

import org.openmrs.module.freeshr.terminology.web.api.Concept;
import org.openmrs.module.freeshr.terminology.web.api.SimpleConceptRepresentation;
import org.openmrs.module.freeshr.terminology.web.config.TrServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class ConceptSetsMapper implements ConceptMappingCommons {

    private TrServerProperties properties;

    @Autowired
    public ConceptSetsMapper(TrServerProperties properties) {
        this.properties = properties;
    }

    @Override
    public Concept map(Concept concept, org.openmrs.Concept openmrsConcept, String requestBaseUrl) {
        concept.setSetMembers(mapSetMembers(openmrsConcept.getSetMembers(), requestBaseUrl));
        return concept;
    }

    private List<SimpleConceptRepresentation> mapSetMembers(List<org.openmrs.Concept> members, String requestBaseUrl) {
        List<SimpleConceptRepresentation> conceptSetMembers = new ArrayList<>();
        for (org.openmrs.Concept member : members) {
            conceptSetMembers.add(getSimplifiedConcept(member, requestBaseUrl));
        }
        return conceptSetMembers;
    }

    private SimpleConceptRepresentation getSimplifiedConcept(org.openmrs.Concept concept, String requestBaseUrl) {
        SimpleConceptRepresentation simpleConceptRepresentation = new SimpleConceptRepresentation();
        simpleConceptRepresentation.setDisplay(concept.getName(Locale.ENGLISH).getName());
        simpleConceptRepresentation.setUuid(concept.getUuid());
        simpleConceptRepresentation.setUri(getConceptUri(concept.getUuid(), requestBaseUrl));
        return simpleConceptRepresentation;
    }

    private String getConceptUri(String uuid, String requestBaseUrl) {
        return properties.getConceptUri(requestBaseUrl) + uuid;
    }
}
