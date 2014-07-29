package org.openmrs.module.freeshr.terminology.web.controller;

import org.openmrs.api.APIAuthenticationException;
import org.openmrs.api.context.Context;
import org.openmrs.module.freeshr.terminology.web.api.Concept;
import org.openmrs.module.freeshr.terminology.web.api.mapper.ConceptMapper;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.RestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/rest/v1/tr/concepts")
public class ConceptController {

    private static final String DISABLE_WWW_AUTH_HEADER_NAME = "Disable-WWW-Authenticate";

    @Autowired
    private ConceptMapper mapper;

    /**
     * @should return unauthorized if not logged in
     * @should return forbidden if logged in
     */
    @ExceptionHandler(APIAuthenticationException.class)
    @ResponseBody
    public SimpleObject apiAuthenticationExceptionHandler(Exception ex, HttpServletRequest request, HttpServletResponse response) throws Exception {
        int errorCode;
        String errorDetail;
        if (Context.isAuthenticated()) {
            // user is logged in but doesn't have the relevant privilege -> 403 FORBIDDEN
            errorCode = HttpServletResponse.SC_FORBIDDEN;
            errorDetail = "User is logged in but doesn't have the relevant privilege";
        } else {
            // user is not logged in -> 401 UNAUTHORIZED
            errorCode = HttpServletResponse.SC_UNAUTHORIZED;
            errorDetail = "User is not logged in";
            if (shouldAddWWWAuthHeader(request)) {
                response.addHeader("WWW-Authenticate", "Basic realm=\"OpenMRS at " + RestConstants.URI_PREFIX + "\"");
            }
        }
        response.setStatus(errorCode);
        return RestUtil.wrapErrorResponse(ex, errorDetail);
    }

    private boolean shouldAddWWWAuthHeader(HttpServletRequest request) {
        return request.getHeader(DISABLE_WWW_AUTH_HEADER_NAME) == null || !request.getHeader(DISABLE_WWW_AUTH_HEADER_NAME).equals("true");
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public Concept getConcept(@PathVariable("uuid") String uuid) {
        final org.openmrs.Concept openmrsConcept = Context.getConceptService().getConceptByUuid(uuid);
        return mapper.map(openmrsConcept);
    }

}
