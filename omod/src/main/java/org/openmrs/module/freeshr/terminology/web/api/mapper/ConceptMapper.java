package org.openmrs.module.freeshr.terminology.web.api.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.openmrs.module.freeshr.terminology.web.api.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

@Component
public class ConceptMapper {

    public Concept map(org.openmrs.Concept openmrsConcept) {
        Concept concept = new Concept();
        concept.setUuid(openmrsConcept.getUuid());
        concept.setVersion(openmrsConcept.getVersion());
        concept.setDatatypeName(openmrsConcept.getDatatype().getName());
        concept.setConceptClass(openmrsConcept.getConceptClass().getName());
        concept.setSet(openmrsConcept.isSet());
        concept.setRetired(openmrsConcept.isRetired());
        concept.setFullySpecifiedName(mapConceptName(openmrsConcept.getFullySpecifiedName(Locale.ENGLISH)));
        concept.setNames(mapConceptNames(openmrsConcept.getNames()));
        concept.setReferenceTerms(mapReferenceTerms(openmrsConcept.getConceptMappings()));
        concept.setDescription(mapDescription(openmrsConcept.getDescription()));
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
        org.openmrs.api.ConceptNameType openmrsConceptNameType = openmrsConceptName.getConceptNameType();
        if (openmrsConceptNameType != null) {
            name.setConceptNameType(openmrsConceptNameType.name());
        }
        return name;
    }

    private List<ConceptReferenceTerm> mapReferenceTerms(Collection<org.openmrs.ConceptMap> openmrsConceptMappings) {
        List<ConceptReferenceTerm> referenceTerms = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(openmrsConceptMappings)) {
            for (org.openmrs.ConceptMap openmrsConceptMap : openmrsConceptMappings) {
                org.openmrs.ConceptReferenceTerm openmrsConceptReferenceTerm = openmrsConceptMap.getConceptReferenceTerm();
                ConceptReferenceTerm referenceTerm = new ConceptReferenceTerm();
                referenceTerm.setUuid(openmrsConceptReferenceTerm.getUuid());
                referenceTerm.setName(openmrsConceptReferenceTerm.getName());
                referenceTerm.setCode(openmrsConceptReferenceTerm.getCode());
                referenceTerm.setDescription(openmrsConceptReferenceTerm.getDescription());
                referenceTerm.setVersion(openmrsConceptReferenceTerm.getVersion());
                referenceTerm.setRetired(openmrsConceptReferenceTerm.isRetired());
                referenceTerm.setMapType(openmrsConceptMap.getConceptMapType().getName());
                referenceTerm.setConceptSource(mapConceptSource(openmrsConceptReferenceTerm.getConceptSource()));
                referenceTerms.add(referenceTerm);
            }
        }
        return referenceTerms;
    }

    private ConceptSource mapConceptSource(org.openmrs.ConceptSource openmrsConceptSource) {
        ConceptSource conceptSource = new ConceptSource();
        conceptSource.setUuid(openmrsConceptSource.getUuid());
        conceptSource.setName(openmrsConceptSource.getName());
        conceptSource.setDescription(openmrsConceptSource.getDescription());
        conceptSource.setHl7Code(openmrsConceptSource.getHl7Code());
        return conceptSource;
    }

    private ConceptDescription mapDescription(org.openmrs.ConceptDescription openmrsConceptDescription) {
        if (openmrsConceptDescription != null) {
            ConceptDescription description = new ConceptDescription();
            description.setUuid(openmrsConceptDescription.getUuid());
            description.setDescription(openmrsConceptDescription.getDescription());
            return description;
        }
        return null;
    }
}
