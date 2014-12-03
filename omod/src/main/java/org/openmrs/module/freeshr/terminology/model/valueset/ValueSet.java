package org.openmrs.module.freeshr.terminology.model.valueset;


import org.codehaus.jackson.annotate.JsonPropertyOrder;

@JsonPropertyOrder({"resourceType", "identifier", "name", "description", "status", "define"})
public class ValueSet {
    private String resourceType = "ValueSet";
    private String identifier;
    private String name;
    private String description;
    private String status;
    private ValueSetDefinition define;

    public ValueSet(String identifier, String name, String description, String status, ValueSetDefinition define) {
        this.identifier = identifier;
        this.name = name;
        this.description = description;
        this.status = status;
        this.define = define;
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

    public ValueSetDefinition getDefine() {
        return define;
    }
}
