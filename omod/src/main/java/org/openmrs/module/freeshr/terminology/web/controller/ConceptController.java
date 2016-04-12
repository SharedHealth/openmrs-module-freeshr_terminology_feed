package org.openmrs.module.freeshr.terminology.web.controller;

import org.openmrs.api.AdministrationService;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.freeshr.terminology.exception.ConceptNotFoundException;
import org.openmrs.module.freeshr.terminology.utils.Constants;
import org.openmrs.module.freeshr.terminology.utils.UrlUtil;
import org.openmrs.module.freeshr.terminology.web.api.Concept;
import org.openmrs.module.freeshr.terminology.web.api.mapper.ConceptMapper;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = Constants.REST_URL_CONCEPT)
public class ConceptController extends BaseRestController {

    private ConceptMapper conceptMapper;
    private ConceptService openmrsConceptService;
    private AdministrationService administrationService;

    @Autowired
    public ConceptController(ConceptMapper conceptMapper, ConceptService conceptService) {
        this(conceptMapper, conceptService, Context.getAdministrationService());
    }

    public ConceptController(ConceptMapper conceptMapper, ConceptService conceptService, AdministrationService administrationService) {
        this.conceptMapper = conceptMapper;
        this.openmrsConceptService = conceptService;
        this.administrationService = administrationService;
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public Concept getConcept(HttpServletRequest httpServletRequest, @PathVariable("uuid") String uuid) {
        final org.openmrs.Concept openmrsConcept = openmrsConceptService.getConceptByUuid(uuid);
        if (openmrsConcept == null) {
            throw new ConceptNotFoundException("No concept found with uuid " + uuid);
        }
        String requestBaseUrl = new UrlUtil().getRequestURL(httpServletRequest, administrationService);
        return conceptMapper.map(openmrsConcept, requestBaseUrl);
    }
}
