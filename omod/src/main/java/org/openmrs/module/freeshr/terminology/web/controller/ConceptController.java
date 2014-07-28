package org.openmrs.module.freeshr.terminology.web.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.freeshr.terminology.web.api.Concept;
import org.openmrs.module.freeshr.terminology.web.api.mapper.ConceptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/rest/v1/tr/concepts")
public class ConceptController {

    @Autowired
    private ConceptMapper mapper;

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public Concept getConcept(@PathVariable("uuid") String uuid) {
        final org.openmrs.Concept openmrsConcept = Context.getConceptService().getConceptByUuid(uuid);
        return mapper.map(openmrsConcept);
    }

}
