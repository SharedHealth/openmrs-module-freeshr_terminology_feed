package org.openmrs.module.freeshr.terminology.web.api.mapper;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.openmrs.module.freeshr.terminology.web.config.TrServerProperties;

import java.io.File;
import java.io.IOException;
import java.net.URLClassLoader;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.openmrs.module.freeshr.terminology.builder.ConceptBuilder.getNumericConcept;
import static org.openmrs.module.freeshr.terminology.builder.ConceptBuilder.getOpenmrsConcept;
import static org.openmrs.module.freeshr.terminology.util.TestUtils.assertConceptNumeric;
import static org.openmrs.module.freeshr.terminology.util.TestUtils.assertConcepts;

public class ConceptMapperTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldMapConcept() throws IOException {
        File src = new File(URLClassLoader.getSystemResource("concept.json").getFile());
        org.openmrs.module.freeshr.terminology.web.api.Concept expected = mapper.readValue(src, org.openmrs.module.freeshr.terminology.web.api.Concept.class);
        ConceptMapper conceptMapper = buildConceptMapper();
        org.openmrs.module.freeshr.terminology.web.api.Concept actual = conceptMapper.map(getOpenmrsConcept());
        assertConcepts(expected, actual);
    }

    @Test
    public void shouldMapPropertiesForNumericConcept() throws IOException {
        File src = new File(URLClassLoader.getSystemResource("concept_numeric.json").getFile());
        org.openmrs.module.freeshr.terminology.web.api.Concept expected = mapper.readValue(src, org.openmrs.module.freeshr.terminology.web.api.Concept.class);
        ConceptMapper conceptMapper = buildConceptMapper();
        org.openmrs.module.freeshr.terminology.web.api.Concept actual = conceptMapper.map(getNumericConcept());
        assertConceptNumeric(expected, actual);
    }

    private ConceptMapper buildConceptMapper() {
        List<ConceptMappingCommons> commonMappings = asList(
                new CommonMappings(),
                new ConceptDescriptionMapper(),
                new ConceptNameMapper(),
                buildReferenceTermMapper(),
                new ConceptSetsMapper()
        );
        List<ConceptMappingExtension> extensions = asList((ConceptMappingExtension) new NumericExtension());
        return new ConceptMapper(commonMappings, extensions);
    }

    private ConceptReferenceTermMapper buildReferenceTermMapper() {
        final TrServerProperties trServerProperties = mock(TrServerProperties.class);
        when(trServerProperties.getConceptReferenceTermUri()).thenReturn("www.bdshr-tr.com/openmrs/ws/rest/v1/conceptreferenceterm/");
        return new ConceptReferenceTermMapper(trServerProperties, new ConceptSourceMapper());
    }
}