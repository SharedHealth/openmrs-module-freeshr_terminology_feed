package org.openmrs.module.freeshr.terminology.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(value = NOT_FOUND, reason = "Concept not found")
public class ConceptNotFoundException extends RuntimeException {

    public ConceptNotFoundException(String message) {
        super(message);
    }
}
