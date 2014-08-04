package org.openmrs.module.freeshr.terminology.web.api;

public class ConceptDescription {

    private String description;
    private String locale;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
