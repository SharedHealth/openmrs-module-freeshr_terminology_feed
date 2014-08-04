package org.openmrs.module.freeshr.terminology.web.api;

public class ConceptName {

    private String conceptName;
    private String conceptNameType;
    private String locale;

    public String getConceptName() {
        return conceptName;
    }

    public void setConceptName(String conceptName) {
        this.conceptName = conceptName;
    }

    public String getConceptNameType() {
        return conceptNameType;
    }

    public void setConceptNameType(String conceptNameType) {
        this.conceptNameType = conceptNameType;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
