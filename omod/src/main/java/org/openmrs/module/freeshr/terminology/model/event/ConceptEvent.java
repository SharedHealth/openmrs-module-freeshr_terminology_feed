package org.openmrs.module.freeshr.terminology.model.event;

import java.util.List;

public interface ConceptEvent {

    public List<String> operations();

    public String getUrl(Object[] argument);

}
