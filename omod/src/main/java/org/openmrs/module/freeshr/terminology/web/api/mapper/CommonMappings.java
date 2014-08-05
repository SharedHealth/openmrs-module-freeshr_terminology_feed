package org.openmrs.module.freeshr.terminology.web.api.mapper;

import org.openmrs.module.freeshr.terminology.web.api.Concept;
import org.springframework.stereotype.Component;

@Component
public class CommonMappings implements ConceptMappingCommons {

    @Override
    public Concept map(Concept concept, org.openmrs.Concept openmrsConcept) {
        concept.setUuid(openmrsConcept.getUuid());
        concept.setVersion(openmrsConcept.getVersion());
        concept.setDatatypeName(openmrsConcept.getDatatype().getName());
        concept.setConceptClass(openmrsConcept.getConceptClass().getName());
        concept.setSet(openmrsConcept.isSet());
        concept.setRetired(openmrsConcept.isRetired());
        concept.setRetireReason(openmrsConcept.getRetireReason());
        return concept;
    }
}
