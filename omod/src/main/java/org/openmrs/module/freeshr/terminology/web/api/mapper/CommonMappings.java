package org.openmrs.module.freeshr.terminology.web.api.mapper;

import org.openmrs.module.freeshr.terminology.web.api.Concept;
import org.openmrs.module.freeshr.terminology.web.config.TrServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonMappings implements ConceptMappingCommons {

    private TrServerProperties properties;

    @Autowired
    public CommonMappings(TrServerProperties properties) {
        this.properties = properties;
    }

    @Override
    public Concept map(Concept concept, org.openmrs.Concept openmrsConcept) {
        concept.setUuid(openmrsConcept.getUuid());
        concept.setVersion(openmrsConcept.getVersion());
        concept.setDatatypeName(openmrsConcept.getDatatype().getName());
        concept.setConceptClass(openmrsConcept.getConceptClass().getName());
        concept.setUri(getConceptUri(openmrsConcept.getUuid()));
        concept.setSet(openmrsConcept.isSet());
        concept.setRetired(openmrsConcept.isRetired());
        concept.setRetireReason(openmrsConcept.getRetireReason());
        return concept;
    }

    private String getConceptUri(String uuid) {
        return properties.getConceptUri() + uuid;
    }
}
