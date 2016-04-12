package org.openmrs.module.freeshr.terminology.web.api.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.openmrs.module.freeshr.terminology.web.api.Concept;
import org.openmrs.module.freeshr.terminology.web.api.ConceptName;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Locale.ENGLISH;

@Component
public class ConceptNameMapper implements ConceptMappingCommons {

    @Override
    public Concept map(Concept concept, org.openmrs.Concept openmrsConcept, String requestBaseUrl) {
        concept.setFullySpecifiedName(mapConceptName(openmrsConcept.getFullySpecifiedName(ENGLISH)));
        concept.setNames(mapConceptNames(openmrsConcept.getNames()));
        return concept;
    }

    private List<ConceptName> mapConceptNames(Collection<org.openmrs.ConceptName> openmrsConceptNames) {
        List<ConceptName> names = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(openmrsConceptNames)) {
            for (org.openmrs.ConceptName openmrsConceptName : openmrsConceptNames) {
                names.add(mapConceptName(openmrsConceptName));
            }
        }
        return names;
    }

    private ConceptName mapConceptName(org.openmrs.ConceptName openmrsConceptName) {
        ConceptName name = new ConceptName();
        name.setConceptName(openmrsConceptName.getName());
        name.setLocale(openmrsConceptName.getLocale().toString());
        name.setPreferred(openmrsConceptName.isPreferred());
        org.openmrs.api.ConceptNameType openmrsConceptNameType = openmrsConceptName.getConceptNameType();
        if (openmrsConceptNameType != null) {
            name.setConceptNameType(openmrsConceptNameType.name());
        }
        return name;
    }
}
