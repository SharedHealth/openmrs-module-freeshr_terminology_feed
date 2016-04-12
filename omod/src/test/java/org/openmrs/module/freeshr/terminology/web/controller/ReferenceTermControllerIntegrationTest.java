package org.openmrs.module.freeshr.terminology.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.freeshr.terminology.exception.ReferenceTermNotFoundException;
import org.openmrs.module.freeshr.terminology.utils.Constants;
import org.openmrs.module.freeshr.terminology.utils.UrlUtil;
import org.openmrs.module.freeshr.terminology.web.api.ConceptReferenceTerm;
import org.openmrs.module.freeshr.terminology.web.api.ConceptReferenceTermMap;
import org.openmrs.module.freeshr.terminology.web.api.mapper.ConceptReferenceTermMapper;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;

@org.springframework.test.context.ContextConfiguration(locations = {"classpath:TestingApplicationContext.xml"}, inheritLocations = true)
public class ReferenceTermControllerIntegrationTest extends BaseModuleWebContextSensitiveTest {

    @Autowired
    private ConceptReferenceTermMapper mapper;
    @Autowired
    private UrlUtil urlUtil;
    private ConceptService service;
    private ReferenceTermController referenceTermController;

    @Before
    public void setUp() {
        service = org.openmrs.api.context.Context.getConceptService();
        referenceTermController = new ReferenceTermController(mapper,service, urlUtil);

    }

    @Test
    public void shouldReturnAReferenceTermByUUID() throws Exception {
        executeDataSet("reference_terms.xml");
        String refTermUuid = "df2d10af-z2w4-49fe-951d-46f614ff6100";
        ConceptReferenceTerm referenceTerm = referenceTermController.getReferenceTerm(buildMockHttpRequest(refTermUuid), refTermUuid);
        assertNotNull(referenceTerm);
        assertEquals("Paracetamol 1",referenceTerm.getName());
        assertEquals("N02BE02",referenceTerm.getCode());
        assertEquals(1,referenceTerm.getConceptReferenceTermMaps().size());

        ConceptReferenceTermMap referenceTermMap = referenceTerm.getConceptReferenceTermMaps().get(0);
        assertEquals("Paracetamol 1",referenceTermMap.getTermA().getDisplay());
        assertEquals("Paracetamol",referenceTermMap.getTermB().getDisplay());

    }

    @Test(expected = ReferenceTermNotFoundException.class)
    public void shouldGiveErrorWhenReferenceTermIsNotPresent() throws Exception {
        executeDataSet("reference_terms.xml");
        String refTermUuid = "df2d10af";
        referenceTermController.getReferenceTerm(buildMockHttpRequest(refTermUuid), refTermUuid);
    }

    private HttpServletRequest buildMockHttpRequest(String uuid) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setScheme("http");
        request.setServerName("tr.com");
        request.setServerPort(8081);
        request.setMethod("GET");
        request.setRequestURI(Constants.REST_URL_REF_TERM + "/" + uuid);
        return request;
    }
}