package org.openmrs.module.freeshr.terminology.web.api.mapper;

import org.openmrs.ConceptNumeric;
import org.openmrs.module.freeshr.terminology.web.api.Concept;
import org.springframework.stereotype.Component;

import static org.openmrs.module.freeshr.terminology.web.api.mapper.MappingConstants.*;

@Component
public class NumericExtension implements ConceptMappingExtension {

    @Override
    public boolean appliesTo(org.openmrs.Concept concept) {
        return concept.isNumeric();
    }

    @Override
    public Concept extend(Concept concept, org.openmrs.Concept openMRSConcept) {
        ConceptNumeric numericConcept = (ConceptNumeric) openMRSConcept;
        addProperty(concept, numericConcept, ABSOLUTE_HIGH.name(), numericConcept.getHiAbsolute());
        addProperty(concept, numericConcept, CRITICAL_HIGH.name(), numericConcept.getHiCritical());
        addProperty(concept, numericConcept, NORMAL_HIGH.name(), numericConcept.getHiNormal());
        addProperty(concept, numericConcept, NORMAL_LOW.name(), numericConcept.getLowNormal());
        addProperty(concept, numericConcept, CRITICAL_LOW.name(), numericConcept.getLowCritical());
        addProperty(concept, numericConcept, ABSOLUTE_LOW.name(), numericConcept.getLowAbsolute());
        addProperty(concept, numericConcept, NUMERIC_PRECISE.name(), numericConcept.getAllowDecimal());
        addProperty(concept, numericConcept, NUMERIC_UNITS.name(), numericConcept.getUnits());
        return concept;
    }

    private void addProperty(Concept concept, ConceptNumeric numericConcept, String name, Object value) {
        if (null != value) {
            concept.addProperty(name, value.toString());
        }
    }
}
