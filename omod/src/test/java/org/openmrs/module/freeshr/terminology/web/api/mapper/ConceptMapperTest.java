package org.openmrs.module.freeshr.terminology.web.api.mapper;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.openmrs.*;
import org.openmrs.api.ConceptNameType;
import org.openmrs.module.freeshr.terminology.web.config.TrServerProperties;

import java.io.File;
import java.io.IOException;
import java.net.URLClassLoader;
import java.util.ArrayList;

import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.openmrs.api.ConceptNameType.*;
import static org.openmrs.module.freeshr.terminology.util.TestUtils.assertConcepts;

public class ConceptMapperTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldMapConcept() throws IOException {
        File src = new File(URLClassLoader.getSystemResource("concept.json").getFile());
        org.openmrs.module.freeshr.terminology.web.api.Concept expected = mapper.readValue(src, org.openmrs.module.freeshr.terminology.web.api.Concept.class);

        final TrServerProperties trServerProperties = mock(TrServerProperties.class);
        final ConceptMapper conceptMapper = new ConceptMapper();
        conceptMapper.setProperties(trServerProperties);
        when(trServerProperties.getConceptReferenceTermUri()).thenReturn("www.bdshr-tr.com/openmrs/ws/rest/v1/conceptreferenceterm/");
        org.openmrs.module.freeshr.terminology.web.api.Concept actual = conceptMapper.map(getOpenmrsConcept());

        assertConcepts(expected, actual);
    }

    private Concept getOpenmrsConcept() {
        Concept openmrsConcept = new Concept();
        openmrsConcept.setUuid("216c8246-202c-4376-bfa8-3278d1049630");
        openmrsConcept.setVersion("1.1.1");
        openmrsConcept.setSet(true);
        openmrsConcept.setRetired(true);
        openmrsConcept.setRetireReason("demo");
        openmrsConcept.setFullySpecifiedName(new ConceptName("tbtest", ENGLISH));

        final ConceptDatatype conceptDatatype = new ConceptDatatype();
        conceptDatatype.setName("Text");
        openmrsConcept.setDatatype(conceptDatatype);

        final ConceptClass conceptClass = new ConceptClass();
        conceptClass.setName("Diagnosis");
        openmrsConcept.setConceptClass(conceptClass);

        openmrsConcept.setNames(asList(createConceptName("search", INDEX_TERM), createConceptName("test", SHORT),
                createConceptName("tbtest", FULLY_SPECIFIED), createConceptName("syn", null)));

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

        openmrsConcept.addSetMember(buildSetMember());

        return openmrsConcept;
    }

    private Concept buildSetMember() {
        Concept concept = new Concept();
        concept.setUuid("92efd6da-d0f8-4806-9093-0b099bf56ce8");
        return concept;
    }

    private ConceptName createConceptName(String conceptNameValue, ConceptNameType conceptNameType) {
        final ConceptName conceptName = new ConceptName(conceptNameValue, ENGLISH);
        conceptName.setConceptNameType(conceptNameType);
        return conceptName;
    }
}