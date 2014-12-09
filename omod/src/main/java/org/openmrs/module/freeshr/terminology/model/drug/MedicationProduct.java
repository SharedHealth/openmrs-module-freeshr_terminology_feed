package org.openmrs.module.freeshr.terminology.model.drug;


import org.openmrs.module.freeshr.terminology.model.CodeableConcept;
import org.openmrs.module.freeshr.terminology.model.Coding;

public class MedicationProduct {
    private CodeableConcept form = new CodeableConcept(null);

    public MedicationProduct(CodeableConcept form) {
        this.form = form;
    }

    public CodeableConcept getForm() {
        return form;
    }
}
