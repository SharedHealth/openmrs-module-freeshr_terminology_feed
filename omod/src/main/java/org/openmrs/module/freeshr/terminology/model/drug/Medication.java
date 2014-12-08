package org.openmrs.module.freeshr.terminology.model.drug;


import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.openmrs.module.freeshr.terminology.model.CodeableConcept;
import org.openmrs.module.freeshr.terminology.model.Coding;

@JsonPropertyOrder({"resourceType","name", "code", "product"})
public class Medication {
    private String resourceType = "Medication";
    private String name;
    private CodeableConcept code;
    private MedicationProduct product;

    public Medication(String name, CodeableConcept code, MedicationProduct product) {
        this.name = name;
        this.code = code;
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public CodeableConcept getCode() {
        return code;
    }

    public MedicationProduct getProduct() {
        return product;
    }

    public String getResourceType() {
        return resourceType;
    }
}