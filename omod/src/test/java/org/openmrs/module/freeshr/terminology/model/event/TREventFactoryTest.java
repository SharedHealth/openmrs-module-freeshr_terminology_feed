package org.openmrs.module.freeshr.terminology.model.event;

import org.ict4h.atomfeed.server.service.Event;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Concept;
import org.openmrs.ConceptClass;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.openmrs.module.freeshr.terminology.model.event.TREventFactory.CONCEPT_URL;
import static org.openmrs.module.freeshr.terminology.model.event.TREventFactory.diagnosisEvent;

public class TREventFactoryTest {

    private static final String CONCEPT_ID = "aebc57b7-0683-464e-ac48-48b8838abdfc";

    private Concept concept;
    private ConceptClass diagnosis;

    @Before
    public void setup() {
        concept = new Concept();
        diagnosis = new ConceptClass();
        diagnosis.setName("Diagnosis");
        concept.setUuid(CONCEPT_ID);
    }

    @Test
    public void shouldCreateConceptEvents() {
        assertThat(TREventFactory.conceptEvent(), is(notNullValue()));
        assertThat(TREventFactory.diagnosisEvent(), is(notNullValue()));
        assertThat(TREventFactory.findingEvent(), is(notNullValue()));
    }

    @Test
    public void shouldBuildAValidDiagnosisEvent() throws Exception {
        concept.setConceptClass(diagnosis);
        Event event = diagnosisEvent().asAtomFeedEvent(new Object[]{concept});

        assertThat(event.getCategory(), is("Diagnosis"));
        assertThat(event.getTitle(), is("Diagnosis"));

        final String url = String.format(CONCEPT_URL, concept.getUuid());
        assertThat(event.getContents(), is(url));
        assertThat(event.getUri().toString(), is(url));
    }
}