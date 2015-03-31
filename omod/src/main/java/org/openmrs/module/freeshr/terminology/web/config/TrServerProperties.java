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
    private static final Logger log = Logger.getLogger(TrServerProperties.class);
    private static final String REST_URI_PREFIX = "webservices.rest.uriPrefix";
    private static final String CONCEPT_URI_CONTEXT_PATH = "concept.uri";
    private static final String CONCEPT_REFERENCE_TERM_URI_CONTEXT_PATH = "concept.reference.term.uri";

    @Resource(name = "trServerDefaultProperties")
    private Properties defaultProperties;
    private Properties trServerProperties;

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

    public String getConceptUri() {
        return getRestUriPrefix() + StringUtil.removePrefix(trServerProperties.getProperty(CONCEPT_URI_CONTEXT_PATH), "/");
    }

    public String getConceptReferenceTermUri() {
        return getRestUriPrefix() + StringUtil.removePrefix(trServerProperties.getProperty(CONCEPT_REFERENCE_TERM_URI_CONTEXT_PATH), "/");
    }

    public String getRestUriPrefix() {
        return StringUtil.ensureSuffix(trServerProperties.getProperty(REST_URI_PREFIX), "/");
    }
}
