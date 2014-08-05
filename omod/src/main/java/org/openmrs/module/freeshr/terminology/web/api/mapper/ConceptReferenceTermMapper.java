package org.openmrs.module.freeshr.terminology.web.api.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.openmrs.ConceptMap;
import org.openmrs.module.freeshr.terminology.web.api.Concept;
import org.openmrs.module.freeshr.terminology.web.api.ConceptReferenceTerm;
import org.openmrs.module.freeshr.terminology.web.config.TrServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
                ConceptReferenceTerm referenceTerm = new ConceptReferenceTerm();
                referenceTerm.setUuid(openmrsConceptReferenceTerm.getUuid());
                referenceTerm.setName(openmrsConceptReferenceTerm.getName());
                referenceTerm.setCode(openmrsConceptReferenceTerm.getCode());
                referenceTerm.setUri(getReferenceTermUri(openmrsConceptReferenceTerm.getUuid()));
                referenceTerm.setDescription(openmrsConceptReferenceTerm.getDescription());
                referenceTerm.setVersion(openmrsConceptReferenceTerm.getVersion());
                referenceTerm.setRetired(openmrsConceptReferenceTerm.isRetired());
                referenceTerm.setMapType(openmrsConceptMap.getConceptMapType().getName());
                referenceTerm.setConceptSource(conceptSourceMapper.mapConceptSource(openmrsConceptReferenceTerm.getConceptSource()));
                referenceTerms.add(referenceTerm);
            }
        }
        return referenceTerms;
    }

    private String getReferenceTermUri(String uuid) {
        return properties.getConceptReferenceTermUri() + uuid;
    }
}
