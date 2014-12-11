package org.openmrs.module.freeshr.terminology.model.event;


import org.ict4h.atomfeed.server.service.Event;
import org.joda.time.DateTime;
import org.openmrs.Drug;

import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;

public class MedicationEvent implements TREvent {

    public static final String MEDICATION_URL = "/openmrs/ws/rest/v1/tr/drugs/%s";

    private List<String> operations() {
        return asList("saveDrug");
    }

    public Boolean isApplicable(String operation) {
        return this.operations().contains(operation);
    }

    public Event asAtomFeedEvent(Object[] arguments) throws URISyntaxException {
        Drug drug = (Drug) arguments[0];
        String url = String.format(MEDICATION_URL, drug.getUuid());
        return new Event(UUID.randomUUID().toString(), "Medication", DateTime.now(), url, url, "Medication");
    }
}