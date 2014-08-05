package org.openmrs.module.freeshr.terminology.web.api.mapper;

import org.openmrs.module.freeshr.terminology.web.api.Concept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConceptMapper {

    private List<ConceptMappingCommons> commonMappings;
    private List<ConceptMappingExtension> conceptMappingExtensions;

    @Autowired
    public ConceptMapper(List<ConceptMappingCommons> commonMappings, List<ConceptMappingExtension> conceptMappingExtensions) {
        this.commonMappings = commonMappings;
        this.conceptMappingExtensions = conceptMappingExtensions;
    }

    public Concept map(org.openmrs.Concept openmrsConcept) {
        Concept concept = new Concept();
        concept = mapCommons(openmrsConcept, concept);
        concept = mapExtensions(openmrsConcept, concept);
        return concept;
    }

    private Concept mapExtensions(org.openmrs.Concept openmrsConcept, Concept concept) {
        for (ConceptMappingExtension conceptMappingExtension : conceptMappingExtensions) {
            if (conceptMappingExtension.appliesTo(openmrsConcept)) {
                concept = conceptMappingExtension.extend(concept, openmrsConcept);
            }
        }
        return concept;
    }

    private Concept mapCommons(org.openmrs.Concept openmrsConcept, Concept concept) {
        for (ConceptMappingCommons commonMapping : commonMappings) {
            concept = commonMapping.map(concept, openmrsConcept);
        }
        return concept;
    }
}
