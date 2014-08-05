package org.openmrs.module.freeshr.terminology.web.api.mapper;

import org.openmrs.module.freeshr.terminology.web.api.ConceptSource;
import org.springframework.stereotype.Component;

@Component
public class ConceptSourceMapper {

    public ConceptSource mapConceptSource(org.openmrs.ConceptSource openmrsConceptSource) {
        ConceptSource conceptSource = new ConceptSource();
        conceptSource.setUuid(openmrsConceptSource.getUuid());
        conceptSource.setName(openmrsConceptSource.getName());
        conceptSource.setDescription(openmrsConceptSource.getDescription());
        conceptSource.setHl7Code(openmrsConceptSource.getHl7Code());
        return conceptSource;
    }
}
