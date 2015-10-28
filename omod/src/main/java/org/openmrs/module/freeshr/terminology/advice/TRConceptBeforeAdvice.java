package org.openmrs.module.freeshr.terminology.advice;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptDatatype;
import org.openmrs.ConceptName;
import org.openmrs.api.APIException;
import org.openmrs.api.ConceptNameType;
import org.openmrs.api.context.Context;
import org.openmrs.module.freeshr.terminology.web.config.TrServerProperties;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

public class TRConceptBeforeAdvice implements MethodBeforeAdvice {
    public static final String VALUESET_CLASS = "Valueset";
    public static final String RETIRE_CONCEPT_METHOD_NAME = "retireConcept";
    public static final String SAVE_CONCEPT_METHOD_NAME = "saveConcept";
    protected final Log logger = LogFactory.getLog(getClass());
    @Override
    public void before(Method method, Object[] arguments, Object service) throws Throwable {
        if (!method.getName().equals(SAVE_CONCEPT_METHOD_NAME)) {
            return;
        }
        Concept concept = (Concept) arguments[0];
        /**
        if (method.getName().equals(RETIRE_CONCEPT_METHOD_NAME)) {
            Collection<ConceptName> conceptNames = concept.getNames();
            for (ConceptName conceptName : conceptNames) {
                if (conceptName.getConceptNameType().equals(ConceptNameType.FULLY_SPECIFIED)) {
                    if (!conceptName.getName().contains("Deprecated")) {
                        conceptName.setName(conceptName.getName() + " (Deprecated)");
                    }
                }
            }
        } **/
        ConceptClass conceptClass = concept.getConceptClass();
        if (conceptClass.getName().equalsIgnoreCase(VALUESET_CLASS)) {
            validateConcept(concept);
        }

    }

    private void validateConcept(Concept concept) {
        List<TrServerProperties> configs = Context.getRegisteredComponents(TrServerProperties.class);
        if (configs != null && !configs.isEmpty()) {
            if (configs.get(0).getValuesetDefinition().equals(TrServerProperties.VALUESET_DEF_ANSWERS)) {
                ensureConceptTypeIsCoded(concept);
                return;
            }
        }
        ensureDefinedAsSet(concept);
    }

    private void ensureDefinedAsSet(Concept concept) {
        if (!concept.isSet()) {
            logger.error("Concept must be a set for class Valueset");
            throw  new APIException("Concept must be a set for class Valueset");
        }
    }


    private void ensureConceptTypeIsCoded(Concept concept) {
        ConceptDatatype datatype = concept.getDatatype();
        if (!datatype.getName().equalsIgnoreCase("Coded")) {
            logger.error("Concept datatype must be coded for class Valueset");
            throw  new APIException("Concept datatype must be coded for class Valueset");
        }
    }
}
