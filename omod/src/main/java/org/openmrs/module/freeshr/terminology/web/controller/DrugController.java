package org.openmrs.module.freeshr.terminology.web.controller;


import org.openmrs.*;
import org.openmrs.api.ConceptService;
import org.openmrs.module.freeshr.terminology.exception.ConceptNotFoundException;
import org.openmrs.module.freeshr.terminology.model.CodeableConcept;
import org.openmrs.module.freeshr.terminology.model.Coding;
import org.openmrs.module.freeshr.terminology.model.drug.Medication;
import org.openmrs.module.freeshr.terminology.model.drug.MedicationProduct;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

@Controller
@RequestMapping(value = "/rest/v1/tr/drugs")
public class DrugController extends BaseRestController {

    private ConceptService openmrsConceptService;
    private static final String CONCEPT_MAP_TYPE_SAME_AS_UUID = "35543629-7d8c-11e1-909d-c80aa9edcf4e";

    @Autowired
    public DrugController(ConceptService conceptService) {
        this.openmrsConceptService = conceptService;
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public Medication getDrug(@PathVariable("uuid") String uuid) {

        Drug drug = openmrsConceptService.getDrugByUuid(uuid);
        if (drug == null) {
            throw new ConceptNotFoundException("No drug found with uuid " + uuid);
        }

        CodeableConcept code = new CodeableConcept(null);
        code.addCoding(getConceptCoding(drug.getConcept()));
        Coding medicationForm = getConceptCoding(drug.getDosageForm());

        return new Medication(drug.getName(), code, new MedicationProduct(medicationForm));
    }

    private Coding getConceptCoding(Concept concept) {
        if (concept == null) {
            return null;
        }

        Collection<ConceptMap> conceptMappings = concept.getConceptMappings();
        for (ConceptMap conceptMap : conceptMappings) {
            if (!conceptMap.getConceptMapType().getUuid().equals(CONCEPT_MAP_TYPE_SAME_AS_UUID)) {
                continue;
            }

            ConceptReferenceTerm referenceTerm = conceptMap.getConceptReferenceTerm();
            String system = "/rest/v1/tr/referenceterms/" + referenceTerm.getUuid();
            return new Coding(system, referenceTerm.getCode(), referenceTerm.getName());
        }

        Collection<ConceptName> names = concept.getNames();
        return new Coding("/rest/v1/tr/concepts/" + concept.getUuid(), concept.getUuid(), concept.getName().getName());
    }
}
