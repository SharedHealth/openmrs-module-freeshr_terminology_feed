package org.openmrs.module.freeshr.terminology.model;


public class ResourceExtension {
    private String url;
    private String valueString;

    public ResourceExtension(String url, String valueString) {
        this.url = url;
        this.valueString = valueString;
    }

    public String getUrl() {
        return url;
    }

    public String getValueString() {
        return valueString;
    }
}
