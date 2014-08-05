package org.openmrs.module.freeshr.terminology.web.api.mapper;

import org.openmrs.ConceptSet;
import org.openmrs.module.freeshr.terminology.web.api.Concept;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class ConceptSetsMapper implements ConceptMappingCommons {

    @Override
    public Concept map(Concept concept, org.openmrs.Concept openmrsConcept) {
        concept.setSetMembers(mapSetMembers(openmrsConcept.getConceptSets()));
        return concept;
    }

    private List<String> mapSetMembers(Collection<ConceptSet> conceptSets) {
        List<String> conceptSetMembers = new ArrayList<>();
        for (ConceptSet conceptSet : conceptSets) {
            conceptSetMembers.add(conceptSet.getConcept().getUuid());
        }
        return conceptSetMembers;
    }
}
