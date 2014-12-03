package org.openmrs.module.freeshr.terminology.web.controller;


import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.ConceptDescription;
import org.openmrs.ConceptName;
import org.openmrs.api.ConceptNameType;
import org.openmrs.api.ConceptService;
import org.openmrs.module.freeshr.terminology.model.valueset.ValueSet;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;

import java.util.Arrays;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@org.springframework.test.context.ContextConfiguration(locations = {"classpath:TestingApplicationContext.xml"}, inheritLocations = true)
public class ValueSetControllerIntegrationTest extends BaseModuleWebContextSensitiveTest {

    ValueSetController vsController;

    ConceptService conceptService;

    @Before
    public void setUp() {
        conceptService = org.openmrs.api.context.Context.getConceptService();
        vsController = new ValueSetController(conceptService);

    }

    @Test
    public void shouldReturnNamedValueSet() throws Exception {
        executeDataSet("concepts.xml");
        ValueSet relationShipVs = vsController.getValueSetByName("patient-contact-relationship");
        assertNotNull(relationShipVs);
        assertEquals("Patient Contact Relationship", relationShipVs.getName());
        assertEquals("values of all relationships as contacts for a patient", relationShipVs.getDescription());
        assertEquals("active", relationShipVs.getStatus());
        assertEquals(2, relationShipVs.getDefine().getConcept().size());

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(relationShipVs));

    }


}
