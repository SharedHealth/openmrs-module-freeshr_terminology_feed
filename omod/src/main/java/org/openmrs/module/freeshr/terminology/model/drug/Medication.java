package org.openmrs.module.freeshr.terminology.model.drug;


import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.openmrs.module.freeshr.terminology.model.CodeableConcept;
import org.openmrs.module.freeshr.terminology.model.ResourceExtension;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"resourceType","name", "code", "product"})
public class Medication {
    private String uuid;
    private String resourceType = "Medication";
    private String name;
    private CodeableConcept code;
    private MedicationProduct product;
    private List<ResourceExtension> extension;

    public Medication(String uuid, String name, CodeableConcept code, MedicationProduct product) {
        this.uuid = uuid;
        this.name = name;
        this.code = code;
        this.product = product;
        this.extension = new ArrayList<>();
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

    public List<ResourceExtension> getExtension() {
        return extension;
    }

    public void addExtension(ResourceExtension extn) {
        extension.add(extn);
    }
}