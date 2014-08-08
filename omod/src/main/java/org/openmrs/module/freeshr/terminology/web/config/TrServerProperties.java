package org.openmrs.module.freeshr.terminology.web.config;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.FileInputStream;
import java.util.Properties;

@Component
public class TrServerProperties {
    private static final Logger log = Logger.getLogger(TrServerProperties.class);
    private static final String CONCEPT_URI = "concept.uri";
    private static final String CONCEPT_REFERENCE_TERM_URI = "concept.reference.term.uri";

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
        return trServerProperties.getProperty(CONCEPT_URI);
    }

    public String getConceptReferenceTermUri() {
        return trServerProperties.getProperty(CONCEPT_REFERENCE_TERM_URI);
    }
}
