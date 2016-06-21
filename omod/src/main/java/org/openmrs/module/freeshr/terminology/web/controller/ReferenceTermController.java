package org.openmrs.module.freeshr.terminology.web.controller;

import org.openmrs.api.ConceptService;
import org.openmrs.module.freeshr.terminology.exception.ReferenceTermNotFoundException;
import org.openmrs.module.freeshr.terminology.utils.Constants;
import org.openmrs.module.freeshr.terminology.utils.UrlUtil;
import org.openmrs.module.freeshr.terminology.web.api.ConceptReferenceTerm;
import org.openmrs.module.freeshr.terminology.web.api.mapper.ConceptReferenceTermMapper;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = Constants.REST_URL_REF_TERM)
public class ReferenceTermController extends BaseRestController {

    private final ConceptReferenceTermMapper mapper;
    private final ConceptService openmrsConceptService;
    private UrlUtil urlUtil;

    @Autowired
    public ReferenceTermController(ConceptReferenceTermMapper mapper, ConceptService conceptService, UrlUtil urlUtil) {
        this.mapper = mapper;
        this.openmrsConceptService = conceptService;
        this.urlUtil = urlUtil;
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public ConceptReferenceTerm getReferenceTerm(HttpServletRequest httpServletRequest, @PathVariable("uuid") String uuid) {
        final org.openmrs.ConceptReferenceTerm openmrsReferenceTerm = openmrsConceptService.getConceptReferenceTermByUuid(uuid);
        if (openmrsReferenceTerm == null) {
            throw new ReferenceTermNotFoundException("No reference term found with uuid " + uuid);
        }
        String requestBaseUrl = urlUtil.getRequestURL(httpServletRequest);
        return mapper.mapReferenceTerm(openmrsReferenceTerm, requestBaseUrl);
    }
}
