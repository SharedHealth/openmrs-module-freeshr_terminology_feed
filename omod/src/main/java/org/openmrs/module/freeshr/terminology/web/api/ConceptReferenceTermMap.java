package org.openmrs.module.freeshr.terminology.web.api;

public class ConceptReferenceTermMap {
    private String uuid;
    private ConceptReferenceMappedTerm termA;
    private ConceptReferenceMappedTerm termB;
    private ConceptMapType conceptMapType;

    public ConceptReferenceTermMap() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ConceptReferenceMappedTerm getTermA() {
        return termA;
    }

    public void setTermA(ConceptReferenceMappedTerm termA) {
        this.termA = termA;
    }

    public ConceptReferenceMappedTerm getTermB() {
        return termB;
    }

    public void setTermB(ConceptReferenceMappedTerm termB) {
        this.termB = termB;
    }

    public ConceptMapType getConceptMapType() {
        return conceptMapType;
    }

    public void setConceptMapType(ConceptMapType conceptMapType) {
        this.conceptMapType = conceptMapType;
    }
}