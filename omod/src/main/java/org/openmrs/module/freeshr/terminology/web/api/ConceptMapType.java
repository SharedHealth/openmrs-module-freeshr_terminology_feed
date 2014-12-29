package org.openmrs.module.freeshr.terminology.web.api;

public class ConceptMapType {
    private String Display;

    public ConceptMapType(String display) {
        Display = display;
    }

    public String getDisplay() {
        return Display;
    }

    public void setDisplay(String display) {
        Display = display;
    }
}