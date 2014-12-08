package org.openmrs.module.freeshr.terminology.model.drug;


import org.openmrs.module.freeshr.terminology.model.CodeableConcept;
import org.openmrs.module.freeshr.terminology.model.Coding;

public class MedicationProduct {
    private CodeableConcept form = new CodeableConcept(null);

    public MedicationProduct(Coding form) {
        this.form.addCoding(form);

    }

    public CodeableConcept getForm() {
        return form;
    }
}
