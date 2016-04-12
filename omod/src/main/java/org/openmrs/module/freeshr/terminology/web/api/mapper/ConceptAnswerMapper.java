package org.openmrs.module.freeshr.terminology.web.api.mapper;

import org.openmrs.ConceptAnswer;
import org.openmrs.module.freeshr.terminology.web.api.Concept;
import org.openmrs.module.freeshr.terminology.web.api.SimpleConceptRepresentation;
import org.openmrs.module.freeshr.terminology.web.config.TrServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ConceptAnswerMapper implements ConceptMappingCommons {

    private TrServerProperties properties;

    @Autowired
    public ConceptAnswerMapper(TrServerProperties properties) {
        this.properties = properties;
    }

    @Override
    public Concept map(Concept concept, org.openmrs.Concept openmrsConcept, String requestBaseUrl) {
        if (null != openmrsConcept.getAnswers()) {
            for (ConceptAnswer conceptAnswer : openmrsConcept.getAnswers()) {
                concept.addAnswer(getSimplifiedConcept(conceptAnswer.getAnswerConcept(), requestBaseUrl));
                //concept.addAnswer(conceptAnswer.getAnswerConcept().getUuid());
            }
        }
        return concept;
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
