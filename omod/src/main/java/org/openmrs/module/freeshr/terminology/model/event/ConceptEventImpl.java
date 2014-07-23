package org.openmrs.module.freeshr.terminology.model.event;

import org.ict4h.atomfeed.server.service.Event;
import org.joda.time.DateTime;
import org.openmrs.Concept;

import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;

public class ConceptEventImpl implements ConceptEvent {

    private final String url;
    private final String title;
    private final String category;

    public ConceptEventImpl(String title, String category, String url) {
        this.title = title;
        this.category = category;
        this.url = url;
    }

    private List<String> operations() {
        return asList("saveConcept", "updateConcept");
    }

    public Boolean isApplicable(String operation) {
        return this.operations().contains(operation);
    }

    @Override
    public Event asAtomFeedEvent(Object[] arguments) throws URISyntaxException {
        Concept concept = (Concept) arguments[0];
        if (isMatchingCategory(concept, this.category)) {
            String url = String.format(this.url, concept.getUuid());
            return new Event(UUID.randomUUID().toString(), this.title, DateTime.now(), url, url, this.category);
        }
        return null;
    }

    private boolean isMatchingCategory(Concept concept, String category) {
        return this.category.equals("concept") ||
                (concept.getConceptClass() != null && concept.getConceptClass().getName().equals(category));
    }

}