package org.openmrs.module.freeshr.terminology.web.api;

public class ConceptReferenceTermMap {

    private String uuid;
    private String conceptMapType;
    private ConceptMapTerm termA;
    private ConceptMapTerm termB;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getConceptMapType() {
        return conceptMapType;
    }

    public void setConceptMapType(String conceptMapType) {
        this.conceptMapType = conceptMapType;
    }

    public ConceptMapTerm getTermA() {
        return termA;
    }

    public void setTermA(ConceptMapTerm termA) {
        this.termA = termA;
    }

    public ConceptMapTerm getTermB() {
        return termB;
    }

    public void setTermB(ConceptMapTerm termB) {
        this.termB = termB;
    }
}
