package org.openmrs.module.freeshr.terminology.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import org.openmrs.ConceptReferenceTerm;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.ConceptService;
import org.openmrs.module.freeshr.terminology.exception.ReferenceTermNotFoundException;
import org.openmrs.module.freeshr.terminology.utils.Constants;
import org.openmrs.module.freeshr.terminology.web.api.mapper.ConceptReferenceTermMapper;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ReferenceTermControllerTest {

    private ReferenceTermController controller;
    @Mock
    private ConceptService openmrsConceptService;
    @Mock
    private ConceptReferenceTermMapper mapper;
    @Mock
    private AdministrationService administrationService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        controller = new ReferenceTermController(mapper, openmrsConceptService, administrationService);
    }

    @Test
    public void shouldReturnConcept() throws IOException {
        final String uuid = "216c8246-202c-4376-bfa8-3278d1049630";
        final ConceptReferenceTerm openmrsReferenceTerm = new ConceptReferenceTerm();
        when(openmrsConceptService.getConceptReferenceTermByUuid(uuid)).thenReturn(openmrsReferenceTerm);
        HttpServletRequest httpServletRequest = buildMockHttpRequest(uuid);
        controller.getReferenceTerm(httpServletRequest, uuid);
        verify(mapper).mapReferenceTerm(openmrsReferenceTerm, "http://tr.com:8081");
    }

    @Test(expected = ReferenceTermNotFoundException.class)
    public void shouldThrowExceptionWhenConceptNotFound() {
        final String uuid = "216c8246-202c-4376-bfa8-3278d1049630";
        when(openmrsConceptService.getConceptReferenceTermByUuid(uuid)).thenReturn(null);
        controller.getReferenceTerm(buildMockHttpRequest(uuid), uuid);
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