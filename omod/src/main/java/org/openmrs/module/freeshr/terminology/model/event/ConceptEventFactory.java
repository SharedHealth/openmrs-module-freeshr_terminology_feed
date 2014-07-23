package org.openmrs.module.freeshr.terminology.model.event;

public class ConceptEventFactory {

    private static final String CONCEPT_URL = "/openmrs/ws/rest/v1/concept/%s?v=full";

    private static final String CUSTOM_URL = "/openmrs/ws/rest/v1/concept/%s?v=custom:(uuid,description,name:(uuid,name,locale,conceptNameType)," +
            "datatype,set,version,retired,names,answers,setMembers,mappings:(uuid,conceptReferenceTerm,conceptMapType))";

    public static ConceptEvent conceptEvent() {
        return new ConceptEventImpl("concept", "concept", CONCEPT_URL);
    }

    public static ConceptEvent diagnosisEvent() {
        return new ConceptEventImpl("Diagnosis", "Diagnosis", CUSTOM_URL);
    }

    public static ConceptEvent findingEVent() {
        return new ConceptEventImpl("Finding", "Finding", CUSTOM_URL);
    }

    public static ConceptEvent labSetEvent() {
        return new ConceptEventImpl("LabSet", "LabSet", CUSTOM_URL);
    }

    public static ConceptEvent medSetEvent() {
        return new ConceptEventImpl("MedSet", "MedSet", CUSTOM_URL);
    }

    public static ConceptEvent symptomEvent() {
        return new ConceptEventImpl("Symptom", "Symptom", CUSTOM_URL);
    }

    public static ConceptEvent drugEvent() {
        return new ConceptEventImpl("Drug", "Drug", CUSTOM_URL);
    }

    public static ConceptEvent testEvent() {
        return new ConceptEventImpl("Test", "Test", CUSTOM_URL);
    }

    public static ConceptEvent referenceTermEvent() {
        return new ConceptReferenceTermEvent();
    }
}
