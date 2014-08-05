package org.openmrs.module.freeshr.terminology.web.api.mapper;

import org.openmrs.module.freeshr.terminology.web.api.Concept;

public interface ConceptMappingCommons {

    public Concept map(Concept concept, org.openmrs.Concept openMRSConcept);
}
