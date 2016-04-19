package org.openmrs.module.freeshr.terminology.web.api.mapper;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.freeshr.terminology.web.config.TrServerProperties;

import java.io.File;
import java.io.IOException;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Properties;

import static java.util.Arrays.asList;
import static org.openmrs.module.freeshr.terminology.builder.ConceptBuilder.buildNumericConcept;
import static org.openmrs.module.freeshr.terminology.builder.ConceptBuilder.buildOpenmrsConcept;
import static org.openmrs.module.freeshr.terminology.util.TestUtils.assertConceptNumeric;
import static org.openmrs.module.freeshr.terminology.util.TestUtils.assertConcepts;
import static org.openmrs.module.freeshr.terminology.web.config.TrServerProperties.CONCEPT_REFERENCE_TERM_URI_CONTEXT_PATH;
import static org.openmrs.module.freeshr.terminology.web.config.TrServerProperties.CONCEPT_URI_CONTEXT_PATH;
import static org.openmrs.module.freeshr.terminology.web.config.TrServerProperties.REST_URI_PREFIX;

public class ConceptMapperTest {

    private static final String BASE_URL = "www.bdshr-tr.com/";
    private ObjectMapper mapper = new ObjectMapper();
    private TrServerProperties trServerProperties;

    @Before
    public void setUp() throws Exception {
        Properties trServerProperties = new Properties();
        trServerProperties.setProperty(REST_URI_PREFIX, "/openmrs/ws");
        trServerProperties.setProperty(CONCEPT_URI_CONTEXT_PATH, "/rest/v1/tr/concepts/");
        trServerProperties.setProperty(CONCEPT_REFERENCE_TERM_URI_CONTEXT_PATH, "/rest/v1/tr/referenceterms/");
        this.trServerProperties = new TrServerProperties(trServerProperties);
    }

    @Test
    public void shouldMapConcept() throws IOException {
        File src = new File(URLClassLoader.getSystemResource("concept.json").getFile());
        org.openmrs.module.freeshr.terminology.web.api.Concept expected = mapper.readValue(src, org.openmrs.module.freeshr.terminology.web.api.Concept.class);
        ConceptMapper conceptMapper = buildConceptMapper();
        org.openmrs.module.freeshr.terminology.web.api.Concept actual = conceptMapper.map(buildOpenmrsConcept("Text"), BASE_URL);
        assertConcepts(expected, actual);
    }

    @Test
    public void shouldMapPropertiesForNumericConcept() throws IOException {
        File src = new File(URLClassLoader.getSystemResource("concept_numeric.json").getFile());
        org.openmrs.module.freeshr.terminology.web.api.Concept expected = mapper.readValue(src, org.openmrs.module.freeshr.terminology.web.api.Concept.class);
        ConceptMapper conceptMapper = buildConceptMapper();
        org.openmrs.module.freeshr.terminology.web.api.Concept actual = conceptMapper.map(buildNumericConcept(), BASE_URL);
        assertConceptNumeric(expected, actual);
    }

    private ConceptMapper buildConceptMapper() {
        List<ConceptMappingCommons> commonMappings = asList(
                buildCommonMapper(),
                new ConceptDescriptionMapper(),
                new ConceptNameMapper(),
                buildReferenceTermMapper(),
                new ConceptSetsMapper(trServerProperties),
                new ConceptAnswerMapper(trServerProperties)
        );
        List<ConceptMappingExtension> extensions = asList((ConceptMappingExtension) new NumericExtension());
        return new ConceptMapper(commonMappings, extensions);
    }

    private CommonMappings buildCommonMapper() {
        return new CommonMappings(trServerProperties);
    }

    private ConceptReferenceTermMapper buildReferenceTermMapper() {
        return new ConceptReferenceTermMapper(trServerProperties, new ConceptSourceMapper());
    }
}