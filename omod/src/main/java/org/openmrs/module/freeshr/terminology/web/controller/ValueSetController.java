package org.openmrs.module.freeshr.terminology.web.controller;

import org.openmrs.*;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.freeshr.terminology.exception.ConceptNotFoundException;
import org.openmrs.module.freeshr.terminology.model.valueset.ValueSet;
import org.openmrs.module.freeshr.terminology.model.valueset.ValueSetCodeSystem;
import org.openmrs.module.freeshr.terminology.model.valueset.ValueSetConcept;
import org.openmrs.module.freeshr.terminology.utils.Constants;
import org.openmrs.module.freeshr.terminology.utils.StringUtil;
import org.openmrs.module.freeshr.terminology.utils.UrlUtil;
import org.openmrs.module.freeshr.terminology.web.config.TrServerProperties;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = Constants.REST_URL_VS)
public class ValueSetController extends BaseRestController {
    private final ConceptService openmrsConceptService;
    private TrServerProperties trServerProperties;
    private UrlUtil urlUtil;

    @Autowired
    public ValueSetController(ConceptService conceptService, TrServerProperties trServerProperties, UrlUtil urlUtil) {
        this.openmrsConceptService = conceptService;
        this.trServerProperties = trServerProperties;
        this.urlUtil = urlUtil;
    }

    @RequestMapping(value = "/{vsName}", method = RequestMethod.GET)
    @ResponseBody
    public ValueSet getValueSet(HttpServletRequest httpServletRequest, @PathVariable("vsName") String vsNameOrUUID) {
        org.openmrs.Concept mrsConcept = null;
        String requestBaseUrl = urlUtil.getRequestURL(httpServletRequest);
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

        ValueSet valueSet = new ValueSet(getIdentifier(mrsConcept, requestBaseUrl),
                getConceptDisplay(mrsConcept),
                getDescription(mrsConcept),
                getStatus(mrsConcept), getDefinition(mrsConcept, requestBaseUrl));
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
        return mrsConcept.getConceptClass().getName().equalsIgnoreCase("Valueset");
    }

    private String getIdentifier(Concept mrsConcept, String requestBaseUrl) {
        String uriPrefix = StringUtil.removeSuffix(trServerProperties.getRestUriPrefix(requestBaseUrl), "/");
        String name = getConceptDisplay(mrsConcept);
        return uriPrefix + StringUtil.ensureSuffix(Constants.REST_URL_VS, "/") + name.replaceAll(" ", "-").toLowerCase();
    }

    private String getDescription(org.openmrs.Concept mrsConcept) {
        ConceptDescription description = mrsConcept.getDescription();
        return description != null ? description.getDescription() : "";
    }

    private String getConceptDisplay(org.openmrs.Concept mrsConcept) {
        return mrsConcept.getName().getName();
    }

    private String getConceptCode(org.openmrs.Concept mrsConcept) {
        String code = getReferenceCode(mrsConcept);
        if (code != null) {
            return code;
        }
        Collection<ConceptName> shortNames = mrsConcept.getShortNames();
        return shortNames.isEmpty() ? mrsConcept.getName().getName() : ((ConceptName) shortNames.toArray()[0]).getName();
    }


    private String getStatus(org.openmrs.Concept mrsConcept) {
        return mrsConcept.isRetired() ? "retired" : "active";
    }

    private ValueSetCodeSystem getDefinition(Concept mrsConcept, String requestBaseUrl) {
        return trServerProperties.getValuesetDefinition().equals(TrServerProperties.VALUESET_DEF_MEMBERS)
             ? getValueSetDefinitionFromMembers(mrsConcept, requestBaseUrl)
             : getValueSetDefinitionFromAnswers(mrsConcept, requestBaseUrl);
    }

    private ValueSetCodeSystem getValueSetDefinitionFromMembers(Concept mrsConcept, String requestBaseUrl) {
        List<Concept> members = mrsConcept.getSetMembers();
        List<ValueSetConcept> valueSetConcepts = new ArrayList<>();
        for (Concept concept : members) {
            if (!concept.isRetired()) {
                valueSetConcepts.add(new ValueSetConcept(
                        getConceptCode(concept),
                        getConceptDisplay(concept),
                        getDescription(concept)));
            }
        }
        return new ValueSetCodeSystem(getIdentifier(mrsConcept, requestBaseUrl), true, valueSetConcepts);
    }

    private ValueSetCodeSystem getValueSetDefinitionFromAnswers(Concept mrsConcept, String requestBaseUrl) {
        ConceptDatatype datatype = mrsConcept.getDatatype();
        List<ValueSetConcept> valueSetConcepts = new ArrayList<ValueSetConcept>();
        if (datatype.getName().equalsIgnoreCase("coded")) {
            Collection<ConceptAnswer> answers = mrsConcept.getAnswers();
            for (ConceptAnswer answer : answers) {
                Concept codedAnswer = answer.getAnswerConcept();
                valueSetConcepts.add(new ValueSetConcept(
                        getConceptCode(codedAnswer),
                        getConceptDisplay(codedAnswer),
                        getDescription(codedAnswer)));

            }
            return new ValueSetCodeSystem(getIdentifier(mrsConcept, requestBaseUrl), true, valueSetConcepts);
        }
        return new ValueSetCodeSystem(getIdentifier(mrsConcept, requestBaseUrl), true, valueSetConcepts);
    }

    private String getReferenceCode(Concept codedAnswer) {
        Collection<ConceptMap> conceptMaps = codedAnswer.getConceptMappings();
        for (ConceptMap conceptMap : conceptMaps) {
            if (conceptMap.getConceptMapType().getUuid().equals(Constants.CONCEPT_MAP_TYPE_SAME_AS_UUID)) {
                return conceptMap.getConceptReferenceTerm().getCode();
            }
        }
        return null;
    }
}
