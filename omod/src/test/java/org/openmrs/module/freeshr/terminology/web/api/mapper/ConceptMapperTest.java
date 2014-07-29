package org.openmrs.module.freeshr.terminology.web.api.mapper;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.openmrs.*;
import org.openmrs.api.ConceptNameType;

import java.io.File;
import java.io.IOException;
import java.net.URLClassLoader;
import java.util.ArrayList;

import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static org.junit.Assert.assertEquals;
import static org.openmrs.api.ConceptNameType.*;

public class ConceptMapperTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldMapConcept() throws IOException {
        File src = new File(URLClassLoader.getSystemResource("concept.json").getFile());
        org.openmrs.module.freeshr.terminology.web.api.Concept expected = mapper.readValue(src, org.openmrs.module.freeshr.terminology.web.api.Concept.class);
        org.openmrs.module.freeshr.terminology.web.api.Concept actual = new ConceptMapper().map(getOpenmrsConcept());
        assertEquals(expected, actual);
    }

    private Concept getOpenmrsConcept() {
        Concept openmrsConcept = new Concept();
        openmrsConcept.setUuid("216c8246-202c-4376-bfa8-3278d1049630");
        openmrsConcept.setVersion("1.1.1");
        openmrsConcept.setSet(false);
        openmrsConcept.setFullySpecifiedName(new ConceptName("tbtest", ENGLISH));

        final ConceptDatatype conceptDatatype = new ConceptDatatype();
        conceptDatatype.setName("Text");
        openmrsConcept.setDatatype(conceptDatatype);

        final ConceptClass conceptClass = new ConceptClass();
        conceptClass.setName("Diagnosis");
        openmrsConcept.setConceptClass(conceptClass);

        openmrsConcept.setNames(asList(createConceptName("search", INDEX_TERM), createConceptName("test", SHORT), createConceptName("tbtest", FULLY_SPECIFIED)));

        final ArrayList<org.openmrs.ConceptDescription> conceptDescriptions = new ArrayList<>();
        final org.openmrs.ConceptDescription conceptDescription = new org.openmrs.ConceptDescription();
        conceptDescription.setDescription("description123");
        conceptDescription.setLocale(ENGLISH);
        conceptDescriptions.add(conceptDescription);
        openmrsConcept.setDescriptions(conceptDescriptions);

        final ArrayList<ConceptMap> conceptMaps = new ArrayList<>();
        final ConceptMap conceptMap = new org.openmrs.ConceptMap();

        final ConceptReferenceTerm conceptReferenceTerm = new ConceptReferenceTerm();
        conceptReferenceTerm.setUuid("565496c2-bdfe-4cce-87d4-92091ab1f67a");
        conceptReferenceTerm.setCode("ICD101");
        conceptReferenceTerm.setRetired(false);
        conceptReferenceTerm.setVersion("1.0");
        conceptReferenceTerm.setDescription("ICD100description");
        conceptReferenceTerm.setName("ICD101");

        final ConceptSource conceptSource = new ConceptSource();
        conceptSource.setUuid("ab587e35-dc14-494e-ac38-d79145670b3b");
        conceptSource.setHl7Code("ICD10");
        conceptSource.setDescription("ICD10");
        conceptSource.setName("ICD10");
        conceptReferenceTerm.setConceptSource(conceptSource);

        final ConceptMapType conceptMapType = new ConceptMapType();
        conceptMapType.setName("SAME-AS");
        conceptMap.setConceptMapType(conceptMapType);
        conceptMap.setConceptReferenceTerm(conceptReferenceTerm);
        conceptMap.setConceptMapId(1);
        conceptMaps.add(conceptMap);
        openmrsConcept.setConceptMappings(conceptMaps);

        return openmrsConcept;
    }

    private ConceptName createConceptName(String conceptNameValue, ConceptNameType conceptNameType) {
        final ConceptName conceptName = new ConceptName(conceptNameValue, ENGLISH);
        conceptName.setConceptNameType(conceptNameType);
        return conceptName;
    }
}