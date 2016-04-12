package org.openmrs.module.freeshr.terminology.web.api.mapper;

import org.openmrs.module.freeshr.terminology.web.api.Concept;
import org.openmrs.module.freeshr.terminology.web.api.ConceptDescription;
import org.springframework.stereotype.Component;

import static java.util.Locale.ENGLISH;

@Component
public class ConceptDescriptionMapper implements ConceptMappingCommons {

    @Override
    public Concept map(Concept concept, org.openmrs.Concept openmrsConcept, String requestBaseUrl) {
        concept.setDescription(mapDescription(openmrsConcept.getDescription(ENGLISH)));
        return concept;
    }

    private ConceptDescription mapDescription(org.openmrs.ConceptDescription openmrsConceptDescription) {
        if (openmrsConceptDescription != null) {
            ConceptDescription description = new ConceptDescription();
            description.setDescription(openmrsConceptDescription.getDescription());
            description.setLocale(openmrsConceptDescription.getLocale().toString());
            return description;
        }
        return null;
    }
}
