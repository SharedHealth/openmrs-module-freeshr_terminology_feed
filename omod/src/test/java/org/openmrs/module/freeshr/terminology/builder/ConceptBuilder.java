package org.openmrs.module.freeshr.terminology.builder;


import org.openmrs.*;
import org.openmrs.api.ConceptNameType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static org.openmrs.api.ConceptNameType.*;

public class ConceptBuilder {

    public static Concept buildOpenmrsConcept(String dataType) {
        Concept openmrsConcept = createConcept(dataType);
        openmrsConcept.setUuid("216c8246-202c-4376-bfa8-3278d1049630");
        openmrsConcept.setVersion("1.1.1");
        openmrsConcept.setSet(true);
        openmrsConcept.setRetired(true);
        openmrsConcept.setRetireReason("demo");
        openmrsConcept.setFullySpecifiedName(new ConceptName("tbtest", ENGLISH));

        final ConceptDatatype conceptDatatype = new ConceptDatatype();
        conceptDatatype.setName(dataType);
        openmrsConcept.setDatatype(conceptDatatype);

        final ConceptClass conceptClass = new ConceptClass();
        conceptClass.setName("Diagnosis");
        openmrsConcept.setConceptClass(conceptClass);

        openmrsConcept.setNames(asList(buildConceptName("search", INDEX_TERM), buildConceptName("test", SHORT),
                buildConceptName("tbtest", FULLY_SPECIFIED), buildConceptName("syn", null)));

        final ArrayList<ConceptDescription> conceptDescriptions = new ArrayList<>();
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

        openmrsConcept.addAnswer(buildAnswer());

        return openmrsConcept;
    }

    private static Concept createConcept(String dataType) {
        if (dataType.equalsIgnoreCase("Numeric")) {
            return new ConceptNumeric();
        } else {
            return new Concept();
        }
    }

    private static ConceptAnswer buildAnswer() {
        ConceptAnswer answer = new ConceptAnswer();
        Concept concept = new Concept();
        concept.setUuid("72e25536-6b37-4eff-9db8-7201d39fd6b3");
        concept.addName(new ConceptName("concept answer 1",ENGLISH));
        answer.setAnswerConcept(concept);
        return answer;
    }

    public static Concept buildNumericConcept() {
        ConceptNumeric conceptNumeric = (ConceptNumeric) buildOpenmrsConcept("Numeric");
        conceptNumeric.setHiAbsolute(10d);
        conceptNumeric.setHiCritical(11d);
        conceptNumeric.setHiNormal(12d);
        conceptNumeric.setLowNormal(13d);
        conceptNumeric.setLowCritical(14d);
        conceptNumeric.setLowAbsolute(15d);
        conceptNumeric.setAllowDecimal(true);
        conceptNumeric.setUnits("test");
        return conceptNumeric;
    }

    private static Concept buildSetMember() {
        Concept concept = new Concept();
        concept.setUuid("92efd6da-d0f8-4806-9093-0b099bf56ce8");
        concept.addName(new ConceptName("concept member 1",ENGLISH));
        return concept;
    }

    private static ConceptName buildConceptName(String conceptNameValue, ConceptNameType conceptNameType) {
        final ConceptName conceptName = new ConceptName(conceptNameValue, ENGLISH);
        conceptName.setConceptNameType(conceptNameType);
        return conceptName;
    }
}
