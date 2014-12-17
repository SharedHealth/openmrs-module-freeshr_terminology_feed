package org.openmrs.module.freeshr.terminology.web.controller;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.ConceptService;
import org.openmrs.module.freeshr.terminology.exception.ConceptNotFoundException;
import org.openmrs.module.freeshr.terminology.model.Coding;
import org.openmrs.module.freeshr.terminology.model.ResourceExtension;
import org.openmrs.module.freeshr.terminology.model.drug.Medication;
import org.openmrs.module.freeshr.terminology.web.config.TrServerProperties;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@org.springframework.test.context.ContextConfiguration(locations = {"classpath:TestingApplicationContext.xml"}, inheritLocations = true)
public class DrugControllerIntegrationTest extends BaseModuleWebContextSensitiveTest {
    private DrugController drugController;
    private ConceptService conceptService;
    @Autowired
    private TrServerProperties trServerProperties;
    @Before
    public void setUp() {
        conceptService = org.openmrs.api.context.Context.getConceptService();
        drugController = new DrugController(conceptService, trServerProperties);
    }


    private void assertCoding(String code, String name, String system, Coding coding) {
        assertEquals(code, coding.getCode());
        assertEquals(name, coding.getDisplay());
        assertTrue(coding.getSystem().endsWith(system));
    }

    @Test
    public void shouldReturnAMedicationWhichDoesNotHaveAMapping() throws Exception {
        executeDataSet("drugs.xml");
        String uuid = "04558a65-0e18-4fc4-bb8d-4f9a89fbec5c";
        Medication medication = drugController.getDrug(uuid);
        Coding medicationCode = medication.getCode().getCoding().get(0);
        Coding medicationProductForm = medication.getProduct().getForm().getCoding().get(0);
        assertNotNull(medication);
        assertEquals("Benedryl", medication.getName());

        assertEquals("20 mg", medication.getExtension().get(0).getValueString());

        assertCoding("ddea03a4-c986-4bcd-b5d7-212b9fc52581", "diphenhydramine", "/rest/v1/tr/concepts/ddea03a4-c986-4bcd-b5d7-212b9fc52581", medicationCode);
        assertCoding("ddea03a4-c986-4bcd-b5d7-212b9fc545dx", "Liquid", "/rest/v1/tr/concepts/ddea03a4-c986-4bcd-b5d7-212b9fc545dx", medicationProductForm);
    }

    @Test(expected = ConceptNotFoundException.class)
    public void shouldThrowAnErrorIfMediactionIsNotPresent() throws Exception {
        executeDataSet("drugs.xml");
        String invalidUuid = "1234";
        drugController.getDrug(invalidUuid);
    }

    @Test
    public void shouldReturnAMedicationWhichHaveAMapping() throws Exception {
        executeDataSet("drugs.xml");
        String uuid = "ada47e63-5988-4f51-8282-e22fb66a7332";
        Medication medication = drugController.getDrug(uuid);
        Coding medicationCode = medication.getCode().getCoding().get(0);
        Coding medicationCode1 = medication.getCode().getCoding().get(1);
        Coding medicationProductForm = medication.getProduct().getForm().getCoding().get(0);
        assertNotNull(medication);
        assertEquals("Crocin 500mg", medication.getName());

        assertEquals("500 mg", medication.getExtension().get(0).getValueString());

        assertCoding("N02BE01", "Paracetamol", "/rest/v1/tr/referenceterms/df2d10af-7bbd-49fe-951d-46f614ff6100", medicationCode);
        assertCoding("99110684-c97a-4a3b-9dad-5e5bf146867a", "Paracetamol", "/rest/v1/tr/concepts/99110684-c97a-4a3b-9dad-5e5bf146867a", medicationCode1);
        assertCoding("99110684-c97a-4a3b-9dad-5e5bf1469x0d", "Tablet", "/rest/v1/tr/concepts/99110684-c97a-4a3b-9dad-5e5bf1469x0d", medicationProductForm);
    }
}