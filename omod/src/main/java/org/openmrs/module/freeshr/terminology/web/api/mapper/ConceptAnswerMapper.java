package org.openmrs.module.freeshr.terminology.web.api.mapper;

import org.openmrs.ConceptAnswer;
import org.openmrs.module.freeshr.terminology.web.api.Concept;
import org.springframework.stereotype.Component;

@Component
public class ConceptAnswerMapper implements ConceptMappingCommons {

    @Override
    public Concept map(Concept concept, org.openmrs.Concept openmrsConcept) {
        if (null != openmrsConcept.getAnswers()) {
            for (ConceptAnswer conceptAnswer : openmrsConcept.getAnswers()) {
                concept.addAnswer(conceptAnswer.getAnswerConcept().getUuid());
            }
        }
        return concept;
    }
}
