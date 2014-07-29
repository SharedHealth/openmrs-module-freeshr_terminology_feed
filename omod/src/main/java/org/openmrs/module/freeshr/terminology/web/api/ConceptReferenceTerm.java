package org.openmrs.module.freeshr.terminology.web.api;

import java.util.List;

public class ConceptReferenceTerm {

    private String uuid;
    private String name;
    private String code;
    private String description;
    private String version;
    private boolean retired;
    private String mapType;
    private ConceptSource conceptSource;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isRetired() {
        return retired;
    }

    public void setRetired(boolean retired) {
        this.retired = retired;
    }

    public String getMapType() {
        return mapType;
    }

    public void setMapType(String mapType) {
        this.mapType = mapType;
    }

    public ConceptSource getConceptSource() {
        return conceptSource;
    }

    public void setConceptSource(ConceptSource conceptSource) {
        this.conceptSource = conceptSource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConceptReferenceTerm)) return false;

        ConceptReferenceTerm that = (ConceptReferenceTerm) o;

        if (retired != that.retired) return false;
        if (!code.equals(that.code)) return false;
        if (!conceptSource.equals(that.conceptSource)) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (!mapType.equals(that.mapType)) return false;
        if (!name.equals(that.name)) return false;
        if (!uuid.equals(that.uuid)) return false;
        if (!version.equals(that.version)) return false;

        return true;
    }
}
