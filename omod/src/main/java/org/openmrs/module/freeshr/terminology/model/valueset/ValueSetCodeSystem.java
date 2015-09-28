package org.openmrs.module.freeshr.terminology.model.valueset;


import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"system", "caseSensitive", "concept"})
public class ValueSetCodeSystem {
    private String system;
    private boolean caseSensitive = true;
    private List<ValueSetConcept> concept;

    public ValueSetCodeSystem(String system, boolean caseSensitive, List<ValueSetConcept> vsConcepts) {
        this.system = system;
        this.caseSensitive = caseSensitive;
        this.concept = vsConcepts;
    }

    public String getSystem() {
        return system;
    }

    public List<ValueSetConcept> getConcept() {
        return concept;
    }

    public void setConcept(List<ValueSetConcept> concept) {
        this.concept = concept;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }
}
