package org.openmrs.module.freeshr.terminology.web.api;

public class ConceptReferenceMappedTerm {
    private String uuid;
    private String display;

    public ConceptReferenceMappedTerm(String uuid, String display) {
        this.uuid = uuid;
        this.display = display;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}