package org.openmrs.module.freeshr.terminology.web.api;

import java.util.*;

public class Concept {

    private String uuid;
    private String uri;
    private String version;
    private String datatypeName;
    private String conceptClass;
    private boolean isSet;
    private boolean isRetired;
    private String retireReason;
    private ConceptName fullySpecifiedName;
    private List<ConceptName> names;
    private List<ConceptReferenceTerm> referenceTerms;
    private ConceptDescription description;
    private List<SimpleConceptRepresentation> setMembers;
    private Map<String, String> properties = new HashMap<>();
    private Set<SimpleConceptRepresentation> conceptAnswers = new HashSet<>();

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
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

    public boolean isRetired() {
        return isRetired;
    }

    public void setRetired(boolean isRetired) {
        this.isRetired = isRetired;
    }

    public String getRetireReason() {
        return retireReason;
    }

    public void setRetireReason(String retireReason) {
        this.retireReason = retireReason;
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

    public List<SimpleConceptRepresentation> getSetMembers() {
        return setMembers;
    }

    public void setSetMembers(List<SimpleConceptRepresentation> setMembers) {
        this.setMembers = setMembers;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public void addProperty(String key, String value) {
        properties.put(key, value);
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    public Set<SimpleConceptRepresentation> getConceptAnswers() {
        return conceptAnswers;
    }

    public void setConceptAnswers(Set<SimpleConceptRepresentation> conceptAnswers) {
        this.conceptAnswers = conceptAnswers;
    }

    public void addAnswer(SimpleConceptRepresentation answer) {
        this.conceptAnswers.add(answer);
    }
}
