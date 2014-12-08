package org.openmrs.module.freeshr.terminology.web.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.ConceptService;
import org.openmrs.module.freeshr.terminology.model.CodeableConcept;
import org.openmrs.module.freeshr.terminology.model.Coding;
import org.openmrs.module.freeshr.terminology.model.drug.Medication;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;

import static org.junit.Assert.*;

@org.springframework.test.context.ContextConfiguration(locations = {"classpath:TestingApplicationContext.xml"}, inheritLocations = true)
public class DrugControllerIntegrationTest extends BaseModuleWebContextSensitiveTest {
    private DrugController drugController;
    private ConceptService conceptService;

    @Before
    public void setUp() {
        conceptService = org.openmrs.api.context.Context.getConceptService();
        drugController = new DrugController(conceptService);
    }

    @Test
    public void shouldReturnAMedication() throws Exception {
        executeDataSet("drugs.xml");
        String uuid = "ada47e63-5988-4f51-8282-e22fb66a7332";
        Medication medication = drugController.getDrug(uuid);
        Coding medicationCode = medication.getCode().getCoding().get(0);
        Coding medicationProductForm = medication.getProduct().getForm().getCoding().get(0);
        assertNotNull(medication);
        assertEquals("Crocin 500mg",medication.getName());
        assertEquals("99110684-c97a-4a3b-9dad-5e5bf146867a", medicationCode.getCode());
        assertEquals("Paracetamol",medicationCode.getDisplay());
        assertEquals("/rest/v1/tr/concepts/99110684-c97a-4a3b-9dad-5e5bf146867a",medicationCode.getSystem());

        assertEquals("99110684-c97a-4a3b-9dad-5e5bf1469x0d", medicationProductForm.getCode());
        assertEquals("Tablet",medicationProductForm.getDisplay());
        assertEquals("/rest/v1/tr/concepts/99110684-c97a-4a3b-9dad-5e5bf1469x0d",medicationProductForm.getSystem());

        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(medication));


//        assertEquals("",);
    }
}