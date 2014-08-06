package org.openmrs.module.freeshr.terminology.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = NOT_FOUND, reason = "Reference term not found")
public class ReferenceTermNotFoundException extends RuntimeException {

    public ReferenceTermNotFoundException(String message) {
        super(message);
    }
}
