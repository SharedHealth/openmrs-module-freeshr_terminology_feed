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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConceptDescription)) return false;

        ConceptDescription that = (ConceptDescription) o;

        if (!description.equals(that.description)) return false;
        if (!locale.equals(that.locale)) return false;

        return true;
    }
}
