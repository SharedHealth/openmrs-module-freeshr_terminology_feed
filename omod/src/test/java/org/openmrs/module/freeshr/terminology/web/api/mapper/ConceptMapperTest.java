package org.openmrs.module.freeshr.terminology.web.api.mapper;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.openmrs.*;
import org.openmrs.Concept;
import org.openmrs.ConceptDescription;
import org.openmrs.ConceptName;
import org.openmrs.ConceptReferenceTerm;
import org.openmrs.ConceptSource;
import org.openmrs.api.ConceptNameType;
import org.openmrs.module.freeshr.terminology.web.api.*;

import java.io.File;
import java.io.IOException;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConceptMapperTest {

    @Test
    public void shouldMapConcept() throws IOException {
        org.openmrs.module.freeshr.terminology.web.api.Concept actualConcept = new ConceptMapper().map(getOpenmrsConcept());

        File src = new File(URLClassLoader.getSystemResource("concept.json").getFile());
        final org.openmrs.module.freeshr.terminology.web.api.Concept expectedConcept = new ObjectMapper().readValue(src, org.openmrs.module.freeshr.terminology.web.api.Concept.class);

        assertEquals(expectedConcept.isSet(),actualConcept.isSet());
        assertEquals(expectedConcept.getConceptClass(), actualConcept.getConceptClass());
        assertEquals(expectedConcept.getVersion(),actualConcept.getVersion());
        assertEquals(expectedConcept.getUuid(),actualConcept.getUuid());
        assertEquals(expectedConcept.getDatatypeName(),actualConcept.getDatatypeName());

        assertEquals(expectedConcept.getDescription().getUuid(),actualConcept.getDescription().getUuid());
        assertEquals(expectedConcept.getDescription().getDescription(),actualConcept.getDescription().getDescription());

        assertEquals(expectedConcept.getFullySpecifiedName().getConceptNameType(),actualConcept.getFullySpecifiedName().getConceptNameType());
        assertEquals(expectedConcept.getFullySpecifiedName().getConceptName(),actualConcept.getFullySpecifiedName().getConceptName());

        assertEquals(expectedConcept.getReferenceTerms().get(0).getUuid(),actualConcept.getReferenceTerms().get(0).getUuid());
        assertEquals(expectedConcept.getReferenceTerms().get(0).getMapType(),actualConcept.getReferenceTerms().get(0).getMapType());
        assertEquals(expectedConcept.getReferenceTerms().get(0).getName(),actualConcept.getReferenceTerms().get(0).getName());

        assertEquals(expectedConcept.getNames().size(),actualConcept.getNames().size());
    }

    private Concept getOpenmrsConcept() throws IOException {
        Concept openmrsConcept = new Concept();
        openmrsConcept.setUuid("216c8246-202c-4376-bfa8-3278d1049630");
        openmrsConcept.setVersion("1.1.1");

        final ConceptDatatype conceptDatatype = new ConceptDatatype();
        conceptDatatype.setName("Text");
        openmrsConcept.setDatatype(conceptDatatype);

        final ConceptClass conceptClass = new ConceptClass();
        conceptClass.setName("Diagnosis");
        openmrsConcept.setConceptClass(conceptClass);

        openmrsConcept.setSet(false);

        openmrsConcept.setFullySpecifiedName(new ConceptName("tbtest", Locale.ENGLISH));

        Collection<ConceptName> conceptNames = new ArrayList<>();
        addConceptName(conceptNames, "search", ConceptNameType.INDEX_TERM);
        addConceptName(conceptNames, "test", ConceptNameType.SHORT);
        addConceptName(conceptNames, "tbtest", ConceptNameType.FULLY_SPECIFIED);
        addConceptName(conceptNames, "syn", null);
        openmrsConcept.setNames(conceptNames);

        final ArrayList<org.openmrs.ConceptDescription> conceptDescriptions = new ArrayList<org.openmrs.ConceptDescription>();
        final org.openmrs.ConceptDescription conceptDescription = new org.openmrs.ConceptDescription();
        conceptDescription.setUuid("ab587e35-dc14-494e-ac38-d79145670b3b");
        conceptDescription.setDescription("description123");
        conceptDescriptions.add(conceptDescription);
        openmrsConcept.setDescriptions(conceptDescriptions);

        final ArrayList<ConceptMap> conceptMaps = new ArrayList<ConceptMap>();
        final ConceptMap conceptMap = new org.openmrs.ConceptMap();

        final ConceptReferenceTerm conceptReferenceTerm = new ConceptReferenceTerm();
        conceptReferenceTerm.setUuid("565496c2-bdfe-4cce-87d4-92091ab1f67a");
        conceptReferenceTerm.setCode("ICD101");
        conceptReferenceTerm.setRetired(false);

        final ConceptSource conceptSource = new ConceptSource();
        conceptSource.setUuid("ab587e35-dc14-494e-ac38-d79145670b3b");
        conceptSource.setHl7Code("ICD10");
        conceptSource.setDescription("ICD10");
        conceptSource.setName("ICD10");
        conceptReferenceTerm.setConceptSource(conceptSource);

        conceptReferenceTerm.setVersion("1.0");
        conceptReferenceTerm.setDescription("ICD100description");
        conceptReferenceTerm.setName("ICD101");

        final ConceptMapType conceptMapType = new ConceptMapType();
        conceptMapType.setName("SAME-AS");
        conceptMap.setConceptMapType(conceptMapType);
        conceptMap.setConceptReferenceTerm(conceptReferenceTerm);
        conceptMap.setConceptMapId(1);
        conceptMaps.add(conceptMap);
        openmrsConcept.setConceptMappings(conceptMaps);

        return openmrsConcept;
    }

    private void addConceptName(Collection<ConceptName> conceptNames, String conceptNameValue, ConceptNameType conceptNameType) {
        final ConceptName conceptName = new ConceptName(conceptNameValue, Locale.ENGLISH);
        conceptName.setConceptNameType(conceptNameType);
        conceptNames.add(conceptName);

    }
}