package org.openmrs.module.freeshr.terminology.web.config;

import org.apache.log4j.Logger;
import org.openmrs.module.freeshr.terminology.utils.StringUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.FileInputStream;
import java.util.Properties;

@Component
public class TrServerProperties {
    public static final String REST_URI_PREFIX = "webservices.rest.uriPrefix";
    public static final String CONCEPT_URI_CONTEXT_PATH = "concept.uri";
    public static final String CONCEPT_REFERENCE_TERM_URI_CONTEXT_PATH = "concept.reference.term.uri";
    public static final String VALUESET_DEF_ANSWERS = "answers";
    public static final String VALUESET_DEF_MEMBERS = "members";
    private static final Logger log = Logger.getLogger(TrServerProperties.class);

    @Resource(name = "trServerDefaultProperties")
    private Properties defaultProperties;
    private Properties trServerProperties;

    public TrServerProperties(Properties trServerProperties) {
        this.trServerProperties = trServerProperties;
    }

    public TrServerProperties() {
    }

    @PostConstruct
    public void init() {
        final String configFilePath = defaultProperties.getProperty("config.file.path");
        try {
            Properties envProperties = new Properties();
            FileInputStream file = new FileInputStream(configFilePath);
            envProperties.load(file);
            trServerProperties = envProperties;

        } catch (Exception ignored) {
            log.info(String.format("TR server property file [%s] not found. Using defaults.", configFilePath));
            trServerProperties = defaultProperties;
        }
    }

    public String getConceptUri(String requestBaseUrl) {
        return getRestUriPrefix(requestBaseUrl) + StringUtil.removePrefix(trServerProperties.getProperty(CONCEPT_URI_CONTEXT_PATH), "/");
    }

    public String getConceptReferenceTermUri(String requestBaseUrl) {
        return getRestUriPrefix(requestBaseUrl) + StringUtil.removePrefix(trServerProperties.getProperty(CONCEPT_REFERENCE_TERM_URI_CONTEXT_PATH), "/");
    }

    public String getRestUriPrefix(String requestBaseUrl) {
        return StringUtil.ensureSuffix(StringUtil.ensureSuffix(requestBaseUrl, "/") + StringUtil.removePrefix(trServerProperties.getProperty(REST_URI_PREFIX), "/"), "/");
    }

    public String getValuesetDefinition() {
        String value = trServerProperties.getProperty("valueset.definition");
        return (value != null && !"".equals(value)) ? value : VALUESET_DEF_MEMBERS;
    }
}
