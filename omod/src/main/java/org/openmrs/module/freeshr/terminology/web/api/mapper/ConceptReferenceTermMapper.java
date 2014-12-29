package org.openmrs.module.freeshr.terminology.web.api.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.openmrs.ConceptMap;
import org.openmrs.module.freeshr.terminology.web.api.*;
import org.openmrs.module.freeshr.terminology.web.config.TrServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
public class ConceptReferenceTermMapper implements ConceptMappingCommons {

    private TrServerProperties properties;
    private ConceptSourceMapper conceptSourceMapper;

    @Autowired
    public ConceptReferenceTermMapper(TrServerProperties properties, ConceptSourceMapper conceptSourceMapper) {
        this.properties = properties;
        this.conceptSourceMapper = conceptSourceMapper;
    }

    @Override
    public Concept map(Concept concept, org.openmrs.Concept openmrsConcept) {
        concept.setReferenceTerms(mapReferenceTerms(openmrsConcept.getConceptMappings()));
        return concept;
    }

    private List<ConceptReferenceTerm> mapReferenceTerms(Collection<ConceptMap> openmrsConceptMappings) {
        List<ConceptReferenceTerm> referenceTerms = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(openmrsConceptMappings)) {
            for (org.openmrs.ConceptMap openmrsConceptMap : openmrsConceptMappings) {
                org.openmrs.ConceptReferenceTerm openmrsConceptReferenceTerm = openmrsConceptMap.getConceptReferenceTerm();
                final ConceptReferenceTerm referenceTerm = mapReferenceTerm(openmrsConceptReferenceTerm);
                referenceTerm.setMapType(openmrsConceptMap.getConceptMapType().getName());
                referenceTerms.add(referenceTerm);
            }
        }
        return referenceTerms;
    }

    public ConceptReferenceTerm mapReferenceTerm(org.openmrs.ConceptReferenceTerm openmrsConceptReferenceTerm) {
        ConceptReferenceTerm referenceTerm = new ConceptReferenceTerm();
        referenceTerm.setUuid(openmrsConceptReferenceTerm.getUuid());
        referenceTerm.setName(openmrsConceptReferenceTerm.getName());
        referenceTerm.setCode(openmrsConceptReferenceTerm.getCode());
        referenceTerm.setUri(getReferenceTermUri(openmrsConceptReferenceTerm.getUuid()));
        referenceTerm.setDescription(openmrsConceptReferenceTerm.getDescription());
        referenceTerm.setVersion(openmrsConceptReferenceTerm.getVersion());
        referenceTerm.setRetired(openmrsConceptReferenceTerm.isRetired());
        referenceTerm.setConceptSource(conceptSourceMapper.mapConceptSource(openmrsConceptReferenceTerm.getConceptSource()));
        referenceTerm.setConceptReferenceTermMaps(getConceptReferenceTermMaps(openmrsConceptReferenceTerm));
        return referenceTerm;
    }

    private List<ConceptReferenceTermMap> getConceptReferenceTermMaps(org.openmrs.ConceptReferenceTerm openmrsConceptReferenceTerm) {
        ArrayList<ConceptReferenceTermMap> conceptReferenceTermMaps = new ArrayList<>();
        Set<org.openmrs.ConceptReferenceTermMap> referenceTermMaps = openmrsConceptReferenceTerm.getConceptReferenceTermMaps();
        for (org.openmrs.ConceptReferenceTermMap referenceTermMap : referenceTermMaps) {
            ConceptMapType conceptMapType = new ConceptMapType(referenceTermMap.getConceptMapType().getName());
            ConceptReferenceTermMap conceptReferenceTermMap = new ConceptReferenceTermMap();
            conceptReferenceTermMap.setUuid(referenceTermMap.getUuid());
            conceptReferenceTermMap.setTermA(mapTerm(referenceTermMap.getTermA()));
            conceptReferenceTermMap.setTermB(mapTerm(referenceTermMap.getTermB()));
            conceptReferenceTermMap.setConceptMapType(conceptMapType);
            conceptReferenceTermMaps.add(conceptReferenceTermMap);
        }
        return conceptReferenceTermMaps;
    }

    private ConceptReferenceMappedTerm mapTerm(org.openmrs.ConceptReferenceTerm referenceTerm) {
        return new ConceptReferenceMappedTerm(referenceTerm.getUuid(),referenceTerm.getName());
    }

    private String getReferenceTermUri(String uuid) {
        return properties.getConceptReferenceTermUri() + uuid;
    }
}
