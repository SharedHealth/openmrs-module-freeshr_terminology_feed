package org.openmrs.module.freeshr.terminology.utils;

import org.junit.Test;
import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.Assert.assertEquals;

@org.springframework.test.context.ContextConfiguration(locations = {"classpath:TestingApplicationContext.xml"}, inheritLocations = true)
public class UrlUtilIT extends BaseModuleWebContextSensitiveTest {
    @Autowired
    private UrlUtil urlUtil;

    @Test
    public void shouldGetRequestUrlSchemeFromHeaders() throws Exception {
        MockHttpServletRequest request = buildMockHttpRequest("http", "tr.com", null, "/openmrs/concepts/uuid");
        request.addHeader("X-Forwarded-Proto", "https");
        String requestURL = urlUtil.getRequestURL(request);
        assertEquals("https://tr.com/openmrs/concepts/uuid", requestURL);
    }

    @Test
    public void shouldGetRequestUrlFromGlobalProperty() throws Exception {
        MockHttpServletRequest request = buildMockHttpRequest("https", "tr.com", 8081, "/openmrs/concepts/uuid");
        Context.getAdministrationService().saveGlobalProperty(new GlobalProperty("webservices.rest.uriPrefix", "http://tr.com/openmrs"));
        String requestURL = urlUtil.getRequestURL(request);
        assertEquals("http://tr.com/openmrs/concepts/uuid", requestURL);
    }

    @Test
    public void shouldGetRequestUrlFromRequest() throws Exception {
        MockHttpServletRequest request = buildMockHttpRequest("http", "tr.com", 8081, "/openmrs/concepts/uuid");
        String requestURL = urlUtil.getRequestURL(request);
        assertEquals("http://tr.com:8081/openmrs/concepts/uuid", requestURL);
    }

    private MockHttpServletRequest buildMockHttpRequest(String scheme, String serverName, Integer serverPort, String path) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setScheme(scheme);
        request.setServerName(serverName);
        if (serverPort != null) request.setServerPort(serverPort);
        request.setMethod("GET");
        request.setRequestURI(path);
        return request;
    }
}