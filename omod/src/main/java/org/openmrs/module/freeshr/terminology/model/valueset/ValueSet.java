package org.openmrs.module.freeshr.terminology.model.valueset;


import org.codehaus.jackson.annotate.JsonPropertyOrder;

@JsonPropertyOrder({"resourceType", "identifier", "name", "description", "status", "codeSystem"})
public class ValueSet {
    private String resourceType = "ValueSet";
    private String identifier;
    private String name;
    private String description;
    private String status;
    private ValueSetCodeSystem codeSystem;

    public ValueSet(String identifier, String name, String description, String status, ValueSetCodeSystem codeSystem) {
        this.identifier = identifier;
        this.name = name;
        this.description = description;
        this.status = status;
        this.codeSystem = codeSystem;
    }

    public String getResourceType() {
        return resourceType;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public ValueSetCodeSystem getCodeSystem() {
        return codeSystem;
    }
}
