package org.openmrs.module.freeshr.terminology.web.api;


public class SimpleConceptRepresentation {
    private String uuid;
    private String display;
    private String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String displayName) {
        this.display = displayName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleConceptRepresentation)) return false;

        SimpleConceptRepresentation that = (SimpleConceptRepresentation) o;

        if (!display.equals(that.display)) return false;
        if (!uri.equals(that.uri)) return false;
        if (!uuid.equals(that.uuid)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + display.hashCode();
        result = 31 * result + uri.hashCode();
        return result;
    }
}
