package org.openmrs.module.freeshr.terminology.advice;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptDatatype;
import org.openmrs.api.APIException;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class TRConceptBeforeAdvice implements MethodBeforeAdvice {
    protected final Log logger = LogFactory.getLog(getClass());
    @Override
    public void before(Method method, Object[] arguments, Object service) throws Throwable {
        if (!method.getName().equals("saveConcept")) {
            return;
        }
        Concept concept = (Concept) arguments[0];
        ConceptClass conceptClass = concept.getConceptClass();
        if (conceptClass.getName().equalsIgnoreCase("Valueset")) {
            ConceptDatatype datatype = concept.getDatatype();
            if (!datatype.getName().equalsIgnoreCase("Coded")) {
                logger.error("Concept datatype must be coded for class Valueset");
                throw  new APIException("Concept datatype must be coded for class Valueset");
            }
        }

    }
}
