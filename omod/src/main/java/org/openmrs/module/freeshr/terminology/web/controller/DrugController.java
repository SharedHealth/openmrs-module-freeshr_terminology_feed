package org.openmrs.module.freeshr.terminology.web.controller;


import org.openmrs.Concept;
import org.openmrs.ConceptMap;
import org.openmrs.ConceptName;
import org.openmrs.ConceptReferenceTerm;
import org.openmrs.Drug;
import org.openmrs.api.ConceptService;
import org.openmrs.module.freeshr.terminology.exception.ConceptNotFoundException;
import org.openmrs.module.freeshr.terminology.model.CodeableConcept;
import org.openmrs.module.freeshr.terminology.model.Coding;
import org.openmrs.module.freeshr.terminology.model.ResourceExtension;
import org.openmrs.module.freeshr.terminology.model.drug.Medication;
import org.openmrs.module.freeshr.terminology.model.drug.MedicationProduct;
import org.openmrs.module.freeshr.terminology.utils.Constants;
import org.openmrs.module.freeshr.terminology.utils.StringUtil;
import org.openmrs.module.freeshr.terminology.web.config.TrServerProperties;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

@Controller
@RequestMapping(value = Constants.REST_URL_DRUG)
public class DrugController extends BaseRestController {
    public static final String FHIR_EXTENSION_URL_PREFIX = "https://sharedhealth.atlassian.net/wiki/display/docs/fhir-extensions";

    private ConceptService openmrsConceptService;
    private TrServerProperties trServerProperties;

    @Autowired
    public DrugController(ConceptService conceptService, TrServerProperties trServerProperties) {
        this.openmrsConceptService = conceptService;
        this.trServerProperties = trServerProperties;
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public Medication getDrug(@PathVariable("uuid") String uuid) {

        Drug drug = openmrsConceptService.getDrugByUuid(uuid);
        if (drug == null) {
            throw new ConceptNotFoundException("No drug found with uuid " + uuid);
        }

        String trUriPrefix = StringUtil.removeSuffix(trServerProperties.getRestUriPrefix(), "/");

        CodeableConcept code = getConceptCoding(drug.getConcept(), trUriPrefix);
        CodeableConcept medicationForm = getConceptCoding(drug.getDosageForm(), trUriPrefix);
        Medication medication = new Medication(drug.getName(), code, new MedicationProduct(medicationForm));
        medication.setId(drug.getUuid());
        medication.addExtension(new ResourceExtension(getFhirExtensionUrl("MedicationStrength"), drug.getStrength()));
        medication.addExtension(new ResourceExtension(getFhirExtensionUrl("MedicationRetired"), drug.isRetired().toString()));
        return medication;
    }

    private String getFhirExtensionUrl(String extensionName) {
        return FHIR_EXTENSION_URL_PREFIX + "#" + extensionName;
    }

    private CodeableConcept getConceptCoding(Concept concept, String uriPrefix) {
        CodeableConcept codeableConcept = new CodeableConcept(null);
        if (concept == null) {
            return null;
        }

        Collection<ConceptMap> conceptMappings = concept.getConceptMappings();
        for (ConceptMap conceptMap : conceptMappings) {
            if (!conceptMap.getConceptMapType().getUuid().equals(Constants.CONCEPT_MAP_TYPE_SAME_AS_UUID)) {
                continue;
            }

            ConceptReferenceTerm referenceTerm = conceptMap.getConceptReferenceTerm();
            String system = uriPrefix + StringUtil.ensureSuffix(Constants.REST_URL_REF_TERM, "/") + referenceTerm.getUuid();
            codeableConcept.addCoding(new Coding(system, referenceTerm.getCode(), referenceTerm.getName()));
        }

        Collection<ConceptName> names = concept.getNames();

        String conceptUrl = uriPrefix + StringUtil.ensureSuffix(Constants.REST_URL_CONCEPT, "/") + concept.getUuid();
        codeableConcept.addCoding(new Coding(conceptUrl, concept.getUuid(), concept.getName().getName()));
        return codeableConcept;
    }
}
