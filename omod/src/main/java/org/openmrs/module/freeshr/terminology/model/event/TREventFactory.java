package org.openmrs.module.freeshr.terminology.model.event;

import org.openmrs.module.freeshr.terminology.utils.Constants;
import org.openmrs.module.freeshr.terminology.utils.StringUtil;

public class TREventFactory {

    static final String CONCEPT_URL = Constants.WS_CONTEXT + Constants.REST_URL_CONCEPT;
    public static final String VALUESET_URL = Constants.WS_CONTEXT + Constants.REST_URL_VS;

    public static TREvent conceptEvent() {
        return new ConceptEvent("concept", "concept", CONCEPT_URL);
    }

    public static TREvent diagnosisEvent() {
        return new ConceptEvent("Diagnosis", "Diagnosis", CONCEPT_URL);
    }

    public static TREvent findingEvent() {
        return new ConceptEvent("Finding", "Finding", CONCEPT_URL);
    }

    public static TREvent labSetEvent() {
        return new ConceptEvent("LabSet", "LabSet", CONCEPT_URL);
    }

    public static TREvent medSetEvent() {
        return new ConceptEvent("MedSet", "MedSet", CONCEPT_URL);
    }

    public static TREvent symptomEvent() {
        return new ConceptEvent("Symptom", "Symptom", CONCEPT_URL);
    }

    public static TREvent drugEvent() {
        return new ConceptEvent("Drug", "Drug", CONCEPT_URL);
    }

    public static TREvent testEvent() {
        return new ConceptEvent("Test", "Test", CONCEPT_URL);
    }

    public static TREvent symptomAndFindingEvent() {
        return new ConceptEvent("Symptom/Finding", "Symptom-Finding", CONCEPT_URL);
    }

    public static TREvent procedureEvent() {
        return new ConceptEvent("Procedure", "Procedure", CONCEPT_URL);
    }

    public static TREvent questionEvent() {
        return new ConceptEvent("Question", "Question", CONCEPT_URL);
    }

    public static TREvent referenceTermEvent() {
        return new ReferenceTermEvent();
    }

    public static TREvent valueSetEvent() {
        return new ConceptEvent("Valueset", "Valueset", VALUESET_URL);
    }

    public static TREvent medicationEvent() {
        return new MedicationEvent();
    }
}
