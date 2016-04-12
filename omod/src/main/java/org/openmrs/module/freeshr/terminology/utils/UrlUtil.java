package org.openmrs.module.freeshr.terminology.utils;

import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class UrlUtil {

    public String getRequestURL(HttpServletRequest request) {
        String requestUrl = getServiceUriFromRequest(request);
        if (requestUrl == null) {
            requestUrl = getBaseUrlFromOpenMrsGlobalProperties();
        }
        return requestUrl != null ? requestUrl : formBaseUrl(request, request.getScheme());
    }

    private String getBaseUrlFromOpenMrsGlobalProperties() {
        AdministrationService administrationService = Context.getAdministrationService();
        String restUri = administrationService.getGlobalProperty("webservices.rest.uriPrefix");
        if (restUri != null)
            return restUri;
        return null;
    }

    private String getServiceUriFromRequest(HttpServletRequest request) {
        String scheme = request.getHeader("X-Forwarded-Proto");
        if (scheme == null) {
            return null;
        }
        return formBaseUrl(request, scheme);
    }

    private String formBaseUrl(HttpServletRequest request, String scheme) {
        String hostname = request.getServerName();
        int port = request.getServerPort();
        String baseUrl = null;
        if (port != 80 && port != 443 && port != -1) {
            baseUrl = scheme + "://" + hostname + ":" + port;
        } else {
            baseUrl = scheme + "://" + hostname;
        }
        return baseUrl;
    }
}
