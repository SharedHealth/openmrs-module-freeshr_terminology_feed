package org.openmrs.module.freeshr.terminology.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import org.openmrs.ConceptReferenceTerm;
import org.openmrs.api.ConceptService;
import org.openmrs.module.freeshr.terminology.exception.ReferenceTermNotFoundException;
import org.openmrs.module.freeshr.terminology.web.api.mapper.ConceptReferenceTermMapper;

import java.io.IOException;

public class ReferenceTermControllerTest {

    private ReferenceTermController controller;
    @Mock
    private ConceptService openmrsConceptService;
    @Mock
    private ConceptReferenceTermMapper mapper;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        controller = new ReferenceTermController(mapper, openmrsConceptService);
    }

    @Test
    public void shouldReturnConcept() throws IOException {
        final String uuid = "216c8246-202c-4376-bfa8-3278d1049630";
        final ConceptReferenceTerm openmrsReferenceTerm = new ConceptReferenceTerm();
        when(openmrsConceptService.getConceptReferenceTermByUuid(uuid)).thenReturn(openmrsReferenceTerm);
        controller.getReferenceTerm(uuid);
        verify(mapper).mapReferenceTerm(openmrsReferenceTerm);
    }

    @Test(expected = ReferenceTermNotFoundException.class)
    public void shouldThrowExceptionWhenConceptNotFound() {
        final String uuid = "216c8246-202c-4376-bfa8-3278d1049630";
        when(openmrsConceptService.getConceptReferenceTermByUuid(uuid)).thenReturn(null);
        controller.getReferenceTerm(uuid);
    }

}