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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConceptName)) return false;

        ConceptName that = (ConceptName) o;

        if (!conceptName.equals(that.conceptName)) return false;
        if (conceptNameType != null ? !conceptNameType.equals(that.conceptNameType) : that.conceptNameType != null)
            return false;
        if (!locale.equals(that.locale)) return false;

        return true;
    }
}
