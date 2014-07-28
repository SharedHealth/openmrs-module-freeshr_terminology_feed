package org.openmrs.module.freeshr.terminology.web.helper;

import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.springframework.stereotype.Component;

@Component
public class ConceptControllerHelper {

    public ConceptService getConceptService() {
        return Context.getConceptService();
    }
}
