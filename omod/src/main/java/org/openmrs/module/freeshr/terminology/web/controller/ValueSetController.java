package org.openmrs.module.freeshr.terminology.web.controller;

import org.openmrs.*;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.freeshr.terminology.exception.ConceptNotFoundException;
import org.openmrs.module.freeshr.terminology.model.valueset.ValueSet;
import org.openmrs.module.freeshr.terminology.model.valueset.ValueSetConcept;
import org.openmrs.module.freeshr.terminology.model.valueset.ValueSetDefinition;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = "/rest/v1/tr/vs")
public class ValueSetController extends BaseRestController {
    private final ConceptService openmrsConceptService;

    @Autowired
    public ValueSetController(ConceptService conceptService) {
        this.openmrsConceptService = conceptService;
    }

    @RequestMapping(value = "/{vsName}", method = RequestMethod.GET)
    @ResponseBody
    public ValueSet getValueSet(@PathVariable("vsName") String vsNameOrUUID) {
        org.openmrs.Concept mrsConcept = null;

        if (isUUID(vsNameOrUUID)) {
            mrsConcept = openmrsConceptService.getConceptByUuid(vsNameOrUUID);
        }

        if (mrsConcept == null) {
            String tentativeName = vsNameOrUUID.replaceAll("-", " ");
            mrsConcept = openmrsConceptService.getConceptByName(tentativeName);
        }

        if (mrsConcept == null) {
            throw new ConceptNotFoundException(String.format("Can not find ValueSet [%s]", vsNameOrUUID));
        }

        if (!isValueSet(mrsConcept)) {
            throw new ConceptNotFoundException(String.format("Can not find ValueSet [%s]", vsNameOrUUID));
        }

        ValueSet valueSet = new ValueSet(getIdentifier(mrsConcept),
                getConceptDisplay(mrsConcept),
                getDescription(mrsConcept),
                getStatus(mrsConcept), getDefinition(mrsConcept));
        return valueSet;
    }

    private boolean isUUID(String vsNameOrUUID) {
        try {
            UUID.fromString(vsNameOrUUID);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    private boolean isValueSet(org.openmrs.Concept mrsConcept) {
        return mrsConcept.getConceptClass().getName().equalsIgnoreCase("ValueSet");
    }

    private String getIdentifier(org.openmrs.Concept mrsConcept) {
        String uriPrefix = Context.getAdministrationService().getGlobalProperty(
                RestConstants.URI_PREFIX_GLOBAL_PROPERTY_NAME);
        String name = getConceptDisplay(mrsConcept);
        return uriPrefix + "/rest/v1/tr/vs/" + name.replaceAll(" ", "-").toLowerCase();
    }

    private String getDescription(org.openmrs.Concept mrsConcept) {
        ConceptDescription description = mrsConcept.getDescription();
        return description != null ? description.getDescription() : "";
    }

    private String getConceptDisplay(org.openmrs.Concept mrsConcept) {
        return mrsConcept.getName().getName();
    }

    private String getConceptCode(org.openmrs.Concept mrsConcept) {
        Collection<ConceptName> shortNames = mrsConcept.getShortNames();
        return shortNames.isEmpty() ? mrsConcept.getName().getName() : ((ConceptName) shortNames.toArray()[0]).getName();
    }


    private String getStatus(org.openmrs.Concept mrsConcept) {
        return mrsConcept.isRetired() ? "retired" : "active";
    }

    private ValueSetDefinition getDefinition(org.openmrs.Concept mrsConcept) {
        ConceptDatatype datatype = mrsConcept.getDatatype();
        if (datatype.getName().equalsIgnoreCase("coded")) {
            Collection<ConceptAnswer> answers = mrsConcept.getAnswers();
            List<ValueSetConcept> valueSetConcepts = new ArrayList<>();
            for (ConceptAnswer answer : answers) {
                Concept codedAnswer = answer.getAnswerConcept();
                valueSetConcepts.add(new ValueSetConcept(
                        getConceptCode(codedAnswer),
                        getConceptDisplay(codedAnswer),
                        getDescription(codedAnswer)));

            }
            return new ValueSetDefinition(getIdentifier(mrsConcept), true, valueSetConcepts);
        }
        return new ValueSetDefinition(getIdentifier(mrsConcept), true, new ArrayList<ValueSetConcept>());
    }


}
