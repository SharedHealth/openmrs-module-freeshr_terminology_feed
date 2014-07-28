package org.openmrs.module.freeshr.terminology.model.event;

public class TREventFactory {

    private static final String CONCEPT_URL_FULL = "/openmrs/ws/rest/v1/concept/%s?v=full";

    private static final String CONCEPT_URL_CUSTOM = "/openmrs/ws/rest/v1/concept/%s?v=custom:(uuid,description,name:(uuid,name,locale,conceptNameType)," +
            "datatype,conceptClass,set,version,retired,names,answers,setMembers,mappings:(uuid,conceptReferenceTerm,conceptMapType))";

    public static TREvent conceptEvent() {
        return new ConceptEvent("concept", "concept", CONCEPT_URL_FULL);
    }

    public static TREvent diagnosisEvent() {
        return new ConceptEvent("Diagnosis", "Diagnosis", CONCEPT_URL_CUSTOM);
    }

    public static TREvent findingEvent() {
        return new ConceptEvent("Finding", "Finding", CONCEPT_URL_CUSTOM);
    }

    public static TREvent labSetEvent() {
        return new ConceptEvent("LabSet", "LabSet", CONCEPT_URL_CUSTOM);
    }

    public static TREvent medSetEvent() {
        return new ConceptEvent("MedSet", "MedSet", CONCEPT_URL_CUSTOM);
    }

    public static TREvent symptomEvent() {
        return new ConceptEvent("Symptom", "Symptom", CONCEPT_URL_CUSTOM);
    }

    public static TREvent drugEvent() {
        return new ConceptEvent("Drug", "Drug", CONCEPT_URL_CUSTOM);
    }

    public static TREvent testEvent() {
        return new ConceptEvent("Test", "Test", CONCEPT_URL_CUSTOM);
    }

    public static TREvent symptomAndFindingEvent() {
        return new ConceptEvent("Symptom/Finding", "Symptom-Finding", CONCEPT_URL_CUSTOM);
    }

    public static TREvent procedureEvent() {
        return new ConceptEvent("Procedure", "Procedure", CONCEPT_URL_CUSTOM);
    }

    public static TREvent questionEvent() {
        return new ConceptEvent("Question", "Question", CONCEPT_URL_CUSTOM);
    }

    public static TREvent referenceTermEvent() {
        return new ReferenceTermEvent();
    }
}
