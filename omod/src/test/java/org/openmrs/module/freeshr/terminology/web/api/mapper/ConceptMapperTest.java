package org.openmrs.module.freeshr.terminology.web.api.mapper;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.Concept;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptDatatype;
import org.openmrs.ConceptName;
import org.openmrs.api.ConceptNameType;

import java.io.File;
import java.io.IOException;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class ConceptMapperTest {

    @Test
    @Ignore
    public void shouldMapConcept() throws IOException {
        org.openmrs.module.freeshr.terminology.web.api.Concept concept = new ConceptMapper().map(getOpenmrsConcept());
        String actual = new ObjectMapper().writeValueAsString(concept);

        File src = new File(URLClassLoader.getSystemResource("concept.json").getFile());
        String expected = FileUtils.readFileToString(src);

        assertEquals(expected, actual);
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


        return openmrsConcept;
    }

    private void addConceptName(Collection<ConceptName> conceptNames, String conceptNameValue, ConceptNameType conceptNameType) {
        final ConceptName conceptName = new ConceptName(conceptNameValue, Locale.ENGLISH);
        conceptName.setConceptNameType(conceptNameType);
        conceptNames.add(conceptName);

    }
}