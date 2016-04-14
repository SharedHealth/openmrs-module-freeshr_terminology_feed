package org.openmrs.module.freeshr.terminology.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.openmrs.api.AdministrationService;
import org.openmrs.api.ConceptService;
import org.openmrs.module.freeshr.terminology.exception.ConceptNotFoundException;
import org.openmrs.module.freeshr.terminology.utils.Constants;
import org.openmrs.module.freeshr.terminology.utils.UrlUtil;
import org.openmrs.module.freeshr.terminology.web.api.mapper.ConceptMapper;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ConceptControllerTest {

    private ConceptController conceptController;
    @Mock
    private ConceptService conceptService;
    @Mock
    private ConceptMapper conceptMapper;
    @Mock
    private UrlUtil urlUtil;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        conceptController = new ConceptController(conceptMapper, conceptService, urlUtil);
    }

    @Test
    public void shouldReturnConcept() throws IOException {
        final String uuid = "216c8246-202c-4376-bfa8-3278d1049630";
        final org.openmrs.Concept openmrsConcept = new org.openmrs.Concept();
        when(conceptService.getConceptByUuid(uuid)).thenReturn(openmrsConcept);
        when(urlUtil.getRequestURL(any(HttpServletRequest.class))).thenReturn("http://tr.com:8081");
        conceptController.getConcept(buildMockHttpRequest(uuid), uuid);
        verify(conceptMapper).map(openmrsConcept, "http://tr.com:8081");
    }

    @Test(expected = ConceptNotFoundException.class)
    public void shouldThrowExceptionWhenConceptNotFound() {
        final String uuid = "216c8246-202c-4376-bfa8-3278d1049630";
        when(conceptService.getConceptByUuid(uuid)).thenReturn(null);
        conceptController.getConcept(buildMockHttpRequest(uuid), uuid);
    }

    private HttpServletRequest buildMockHttpRequest(String uuid) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setScheme("http");
        request.setServerName("tr.com");
        request.setServerPort(8081);
        request.setMethod("GET");
        request.setRequestURI(Constants.REST_URL_CONCEPT + "/" + uuid);
        return request;
    }
}