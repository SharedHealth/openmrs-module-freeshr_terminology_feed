package org.openmrs.module.freeshr.terminology.web.api.mapper;


import org.openmrs.module.freeshr.terminology.web.api.Concept;

public interface ConceptMappingExtension {

    public boolean appliesTo(org.openmrs.Concept concept);

    public Concept extend(Concept concept, org.openmrs.Concept openMRSConcept);
}
