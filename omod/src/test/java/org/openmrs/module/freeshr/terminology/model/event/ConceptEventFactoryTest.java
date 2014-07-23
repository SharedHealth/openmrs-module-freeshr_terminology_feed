package org.openmrs.module.freeshr.terminology.model.event;

import org.ict4h.atomfeed.server.service.Event;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Concept;
import org.openmrs.ConceptClass;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.openmrs.module.freeshr.terminology.model.event.ConceptEventFactory.conceptEvent;
import static org.openmrs.module.freeshr.terminology.model.event.ConceptEventFactory.diagnosisEvent;

public class ConceptEventFactoryTest {


    private static final String CONCEPT_ID = "uuid";
    private static final String CUSTOM_URL = "/openmrs/ws/rest/v1/concept/uuid?v=custom:(uuid,description,name:(uuid,name,locale,conceptNameType)," +
            "datatype,set,version,retired,names,answers,setMembers,mappings:(uuid,conceptReferenceTerm,conceptMapType))";

    private Concept concept;
    private ConceptClass diagnosisClass;

    @Before
    public void setup() {
        concept = new Concept();
        diagnosisClass = new ConceptClass();
        diagnosisClass.setName("Diagnosis");
        concept.setUuid(CONCEPT_ID);
    }

    @Test
    public void shouldCreateConceptEvents() {
        assertThat(ConceptEventFactory.conceptEvent(), is(notNullValue()));
        assertThat(ConceptEventFactory.diagnosisEvent(), is(notNullValue()));
        assertThat(ConceptEventFactory.findingEvent(), is(notNullValue()));
    }

    @Test
    public void shouldBuildAValidConceptEvent() throws Exception {
        Event event = conceptEvent().asAtomFeedEvent(new Object[]{concept});
        String url = "/openmrs/ws/rest/v1/concept/" + CONCEPT_ID + "?v=full";
        assertThat(event.getCategory(), is("concept"));
        assertThat(event.getTitle(), is("concept"));
        assertThat(event.getContents(), is(url));
        assertThat(event.getUri().toString(), is(url));
    }

    @Test
    public void shouldBuildAValidDiagnosisEvent() throws Exception {
        concept.setConceptClass(diagnosisClass);
        Event event = diagnosisEvent().asAtomFeedEvent(new Object[]{concept});
        assertThat(event.getCategory(), is("Diagnosis"));
        assertThat(event.getTitle(), is("Diagnosis"));
        assertThat(event.getContents(), is(CUSTOM_URL));
        assertThat(event.getUri().toString(), is(CUSTOM_URL));
    }

}