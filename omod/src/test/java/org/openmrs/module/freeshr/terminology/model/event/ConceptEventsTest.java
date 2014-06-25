package org.openmrs.module.freeshr.terminology.model.event;

import org.ict4h.atomfeed.server.service.Event;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.Concept;
import org.openmrs.ConceptClass;
import org.openmrs.module.freeshr.terminology.utils.Lambda;

import java.net.URISyntaxException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.openmrs.module.freeshr.terminology.utils.Lambda.filter;

public class ConceptEventsTest {

    private static final java.lang.String DIAGNOSIS_UUID = "abcd";

    @Mock
    private Concept diagnosis;

    @Mock
    private ConceptClass conceptClass;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateConceptClassEvents() throws URISyntaxException {
        when(conceptClass.getName()).thenReturn("Diagnosis");
        when(diagnosis.getUuid()).thenReturn(DIAGNOSIS_UUID);
        when(diagnosis.getConceptClass()).thenReturn(conceptClass);

        List<Event> events = new ConceptEvents().asEvents("saveConcept", new Object[]{diagnosis});

        assertThat(events.size(), is(2));
        assertThat(filter(events, isDiagnosis()).size(), is(1));
    }

    private Lambda.Fn<Event, Boolean> isDiagnosis() {
        return new Lambda.Fn<Event, Boolean>() {
            @Override
            public Boolean call(Event input) {
                return input.getCategory().equals("Diagnosis");
            }
        };
    }

}