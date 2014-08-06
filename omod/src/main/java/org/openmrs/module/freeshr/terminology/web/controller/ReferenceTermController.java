package org.openmrs.module.freeshr.terminology.web.controller;

import org.openmrs.api.ConceptService;
import org.openmrs.module.freeshr.terminology.exception.ReferenceTermNotFoundException;
import org.openmrs.module.freeshr.terminology.web.api.ConceptReferenceTerm;
import org.openmrs.module.freeshr.terminology.web.api.mapper.ConceptReferenceTermMapper;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/rest/v1/tr/referenceterms")
public class ReferenceTermController extends BaseRestController {

    private final ConceptReferenceTermMapper mapper;
    private final ConceptService openmrsConceptService;

    @Autowired
    public ReferenceTermController(ConceptReferenceTermMapper mapper, ConceptService conceptService) {
        this.mapper = mapper;
        this.openmrsConceptService = conceptService;
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public ConceptReferenceTerm getReferenceTerm(@PathVariable("uuid") String uuid) {
        final org.openmrs.ConceptReferenceTerm openmrsReferenceTerm = openmrsConceptService.getConceptReferenceTermByUuid(uuid);
        if (openmrsReferenceTerm == null) {
            throw new ReferenceTermNotFoundException("No reference term found with uuid " + uuid);
        }
        return mapper.mapReferenceTerm(openmrsReferenceTerm);
    }
}
