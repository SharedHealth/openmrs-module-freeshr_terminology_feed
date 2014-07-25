package org.openmrs.module.freeshr.terminology.web.api;

import java.util.List;

public class Concept {

    private String uuid;
    private String version;
    private String datatypeName;
    private String conceptClass;
    private boolean isSet;
    private ConceptName fullySpecifiedName;
    private List<ConceptName> names;
    private List<ConceptReferenceTerm> referenceTerms;
    private ConceptDescription description;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDatatypeName() {
        return datatypeName;
    }

    public void setDatatypeName(String datatypeName) {
        this.datatypeName = datatypeName;
    }

    public String getConceptClass() {
        return conceptClass;
    }

    public void setConceptClass(String conceptClass) {
        this.conceptClass = conceptClass;
    }

    public boolean isSet() {
        return isSet;
    }

    public void setSet(boolean isSet) {
        this.isSet = isSet;
    }

    public ConceptName getFullySpecifiedName() {
        return fullySpecifiedName;
    }

    public void setFullySpecifiedName(ConceptName fullySpecifiedName) {
        this.fullySpecifiedName = fullySpecifiedName;
    }

    public List<ConceptName> getNames() {
        return names;
    }

    public void setNames(List<ConceptName> names) {
        this.names = names;
    }

    public List<ConceptReferenceTerm> getReferenceTerms() {
        return referenceTerms;
    }

    public void setReferenceTerms(List<ConceptReferenceTerm> referenceTerms) {
        this.referenceTerms = referenceTerms;
    }

    public ConceptDescription getDescription() {
        return description;
    }

    public void setDescription(ConceptDescription description) {
        this.description = description;
    }
}
