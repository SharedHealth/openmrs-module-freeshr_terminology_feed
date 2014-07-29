package org.openmrs.module.freeshr.terminology.web.api;

public class ConceptSource {

    private String uuid;
    private String name;
    private String description;
    private String hl7Code;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHl7Code() {
        return hl7Code;
    }

    public void setHl7Code(String hl7Code) {
        this.hl7Code = hl7Code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConceptSource)) return false;

        ConceptSource that = (ConceptSource) o;

        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (!hl7Code.equals(that.hl7Code)) return false;
        if (!name.equals(that.name)) return false;
        if (!uuid.equals(that.uuid)) return false;

        return true;
    }
}
