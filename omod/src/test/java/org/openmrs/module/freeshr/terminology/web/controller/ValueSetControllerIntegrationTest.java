package org.openmrs.module.freeshr.terminology.web.controller;


import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.freeshr.terminology.exception.ConceptNotFoundException;
import org.openmrs.module.freeshr.terminology.model.valueset.ValueSet;
import org.openmrs.module.freeshr.terminology.utils.Constants;
import org.openmrs.module.freeshr.terminology.web.config.TrServerProperties;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@org.springframework.test.context.ContextConfiguration(locations = {"classpath:TestingApplicationContext.xml"}, inheritLocations = true)
public class ValueSetControllerIntegrationTest extends BaseModuleWebContextSensitiveTest {

    ValueSetController vsController;

    ConceptService conceptService;
    @Autowired
    TrServerProperties trServerProperties;

    @Before
    public void setUp() {
        conceptService = org.openmrs.api.context.Context.getConceptService();
        vsController = new ValueSetController(conceptService, trServerProperties, Context.getAdministrationService());

    }

    @Test
    public void shouldReturnNamedValueSetByName() throws Exception {
        executeDataSet("concepts.xml");
        String valuesetName = "patient-contact-relationship";
        ValueSet relationShipVs = vsController.getValueSet(buildMockHttpRequest(valuesetName), valuesetName);
        assertNotNull(relationShipVs);
        assertEquals("Patient Contact Relationship", relationShipVs.getName());
        assertEquals("values of all relationships as contacts for a patient", relationShipVs.getDescription());
        assertEquals("active", relationShipVs.getStatus());
        assertEquals(2, relationShipVs.getCodeSystem().getConcept().size());
    }

    @Test
    public void shouldReturnNamedValueSetByUUID() throws Exception {
        executeDataSet("concepts.xml");
        String valuesetName = "8d4a4488-c2cc-11de-8d13-0010c6dffd0f";
        ValueSet relationShipVs = vsController.getValueSet(buildMockHttpRequest(valuesetName), valuesetName);
        assertNotNull(relationShipVs);
        assertEquals("Patient Contact Relationship", relationShipVs.getName());
        assertEquals("values of all relationships as contacts for a patient", relationShipVs.getDescription());
        assertEquals("active", relationShipVs.getStatus());
        assertEquals(2, relationShipVs.getCodeSystem().getConcept().size());
    }

    @Test(expected = ConceptNotFoundException.class)
    public void shouldThrowExceptionWhenThereAreNoValueSets() throws Exception {
        executeDataSet("concepts.xml");
        String valuesetName = "invalid_name";
        ValueSet relationShipVs = vsController.getValueSet(buildMockHttpRequest(valuesetName), valuesetName);
    }

    private HttpServletRequest buildMockHttpRequest(String valuesetName) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setScheme("http");
        request.setServerName("tr.com");
        request.setServerPort(8081);
        request.setMethod("GET");
        request.setRequestURI(Constants.REST_URL_VS + "/" + valuesetName);
        return request;
    }
}
