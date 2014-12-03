package org.openmrs.module.freeshr.terminology.model.valueset;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

@JsonPropertyOrder({"code", "display", "definition"})
public class ValueSetConcept {
    private String code;
    private String display;
    private String definition;

    public ValueSetConcept(String code, String display, String definition) {
        this.code = code;
        this.display = display;
        this.definition = definition;
    }

    public String getCode() {
        return code;
    }

    public String getDisplay() {
        return display;
    }

    public String getDefinition() {
        return definition;
    }
}
