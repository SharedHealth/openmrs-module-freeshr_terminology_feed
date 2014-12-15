package org.openmrs.module.freeshr.terminology.web.api.mapper;

import static java.util.Arrays.asList;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.openmrs.module.freeshr.terminology.builder.ConceptBuilder.buildNumericConcept;
import static org.openmrs.module.freeshr.terminology.builder.ConceptBuilder.buildOpenmrsConcept;
import static org.openmrs.module.freeshr.terminology.util.TestUtils.assertConceptNumeric;
import static org.openmrs.module.freeshr.terminology.util.TestUtils.assertConcepts;
import org.openmrs.module.freeshr.terminology.web.config.TrServerProperties;

import java.io.File;
import java.io.IOException;
import java.net.URLClassLoader;
import java.util.List;

public class ConceptMapperTest {

    public static final String TR_REST_URL = "www.bdshr-tr.com/openmrs/ws/rest/v1/tr/";
    private ObjectMapper mapper = new ObjectMapper();
    @Mock
    private TrServerProperties trServerProperties;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldMapConcept() throws IOException {
        File src = new File(URLClassLoader.getSystemResource("concept.json").getFile());
        org.openmrs.module.freeshr.terminology.web.api.Concept expected = mapper.readValue(src, org.openmrs.module.freeshr.terminology.web.api.Concept.class);
        ConceptMapper conceptMapper = buildConceptMapper();
        org.openmrs.module.freeshr.terminology.web.api.Concept actual = conceptMapper.map(buildOpenmrsConcept("Text"));
        //mapper.writeValue(System.out,actual);
        assertConcepts(expected, actual);
    }

    @Test
    public void shouldMapPropertiesForNumericConcept() throws IOException {
        File src = new File(URLClassLoader.getSystemResource("concept_numeric.json").getFile());
        org.openmrs.module.freeshr.terminology.web.api.Concept expected = mapper.readValue(src, org.openmrs.module.freeshr.terminology.web.api.Concept.class);
        ConceptMapper conceptMapper = buildConceptMapper();
        org.openmrs.module.freeshr.terminology.web.api.Concept actual = conceptMapper.map(buildNumericConcept());
        //mapper.writeValue(System.out,actual);
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
        when(trServerProperties.getConceptUri()).thenReturn(TR_REST_URL + "concepts/");
        return new CommonMappings(trServerProperties);
    }

    private ConceptReferenceTermMapper buildReferenceTermMapper() {
        when(trServerProperties.getConceptReferenceTermUri()).thenReturn(TR_REST_URL + "referenceterms/");
        return new ConceptReferenceTermMapper(trServerProperties, new ConceptSourceMapper());
    }
}