package skyy.blue.web.rest;

import skyy.blue.ProvisionercloudApp;
import skyy.blue.domain.Features;
import skyy.blue.repository.FeaturesRepository;
import skyy.blue.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static skyy.blue.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FeaturesResource} REST controller.
 */
@SpringBootTest(classes = ProvisionercloudApp.class)
public class FeaturesResourceIT {

    private static final Integer DEFAULT_PROXY = 1;
    private static final Integer UPDATED_PROXY = 2;

    private static final Integer DEFAULT_FRIEND = 1;
    private static final Integer UPDATED_FRIEND = 2;

    private static final Integer DEFAULT_RELAY = 1;
    private static final Integer UPDATED_RELAY = 2;

    private static final Integer DEFAULT_LOW_POWER = 1;
    private static final Integer UPDATED_LOW_POWER = 2;

    @Autowired
    private FeaturesRepository featuresRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restFeaturesMockMvc;

    private Features features;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FeaturesResource featuresResource = new FeaturesResource(featuresRepository);
        this.restFeaturesMockMvc = MockMvcBuilders.standaloneSetup(featuresResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Features createEntity(EntityManager em) {
        Features features = new Features()
            .proxy(DEFAULT_PROXY)
            .friend(DEFAULT_FRIEND)
            .relay(DEFAULT_RELAY)
            .lowPower(DEFAULT_LOW_POWER);
        return features;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Features createUpdatedEntity(EntityManager em) {
        Features features = new Features()
            .proxy(UPDATED_PROXY)
            .friend(UPDATED_FRIEND)
            .relay(UPDATED_RELAY)
            .lowPower(UPDATED_LOW_POWER);
        return features;
    }

    @BeforeEach
    public void initTest() {
        features = createEntity(em);
    }

    @Test
    @Transactional
    public void createFeatures() throws Exception {
        int databaseSizeBeforeCreate = featuresRepository.findAll().size();

        // Create the Features
        restFeaturesMockMvc.perform(post("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(features)))
            .andExpect(status().isCreated());

        // Validate the Features in the database
        List<Features> featuresList = featuresRepository.findAll();
        assertThat(featuresList).hasSize(databaseSizeBeforeCreate + 1);
        Features testFeatures = featuresList.get(featuresList.size() - 1);
        assertThat(testFeatures.getProxy()).isEqualTo(DEFAULT_PROXY);
        assertThat(testFeatures.getFriend()).isEqualTo(DEFAULT_FRIEND);
        assertThat(testFeatures.getRelay()).isEqualTo(DEFAULT_RELAY);
        assertThat(testFeatures.getLowPower()).isEqualTo(DEFAULT_LOW_POWER);
    }

    @Test
    @Transactional
    public void createFeaturesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = featuresRepository.findAll().size();

        // Create the Features with an existing ID
        features.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeaturesMockMvc.perform(post("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(features)))
            .andExpect(status().isBadRequest());

        // Validate the Features in the database
        List<Features> featuresList = featuresRepository.findAll();
        assertThat(featuresList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFeatures() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList
        restFeaturesMockMvc.perform(get("/api/features?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(features.getId().intValue())))
            .andExpect(jsonPath("$.[*].proxy").value(hasItem(DEFAULT_PROXY)))
            .andExpect(jsonPath("$.[*].friend").value(hasItem(DEFAULT_FRIEND)))
            .andExpect(jsonPath("$.[*].relay").value(hasItem(DEFAULT_RELAY)))
            .andExpect(jsonPath("$.[*].lowPower").value(hasItem(DEFAULT_LOW_POWER)));
    }
    
    @Test
    @Transactional
    public void getFeatures() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get the features
        restFeaturesMockMvc.perform(get("/api/features/{id}", features.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(features.getId().intValue()))
            .andExpect(jsonPath("$.proxy").value(DEFAULT_PROXY))
            .andExpect(jsonPath("$.friend").value(DEFAULT_FRIEND))
            .andExpect(jsonPath("$.relay").value(DEFAULT_RELAY))
            .andExpect(jsonPath("$.lowPower").value(DEFAULT_LOW_POWER));
    }

    @Test
    @Transactional
    public void getNonExistingFeatures() throws Exception {
        // Get the features
        restFeaturesMockMvc.perform(get("/api/features/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeatures() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        int databaseSizeBeforeUpdate = featuresRepository.findAll().size();

        // Update the features
        Features updatedFeatures = featuresRepository.findById(features.getId()).get();
        // Disconnect from session so that the updates on updatedFeatures are not directly saved in db
        em.detach(updatedFeatures);
        updatedFeatures
            .proxy(UPDATED_PROXY)
            .friend(UPDATED_FRIEND)
            .relay(UPDATED_RELAY)
            .lowPower(UPDATED_LOW_POWER);

        restFeaturesMockMvc.perform(put("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFeatures)))
            .andExpect(status().isOk());

        // Validate the Features in the database
        List<Features> featuresList = featuresRepository.findAll();
        assertThat(featuresList).hasSize(databaseSizeBeforeUpdate);
        Features testFeatures = featuresList.get(featuresList.size() - 1);
        assertThat(testFeatures.getProxy()).isEqualTo(UPDATED_PROXY);
        assertThat(testFeatures.getFriend()).isEqualTo(UPDATED_FRIEND);
        assertThat(testFeatures.getRelay()).isEqualTo(UPDATED_RELAY);
        assertThat(testFeatures.getLowPower()).isEqualTo(UPDATED_LOW_POWER);
    }

    @Test
    @Transactional
    public void updateNonExistingFeatures() throws Exception {
        int databaseSizeBeforeUpdate = featuresRepository.findAll().size();

        // Create the Features

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeaturesMockMvc.perform(put("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(features)))
            .andExpect(status().isBadRequest());

        // Validate the Features in the database
        List<Features> featuresList = featuresRepository.findAll();
        assertThat(featuresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFeatures() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        int databaseSizeBeforeDelete = featuresRepository.findAll().size();

        // Delete the features
        restFeaturesMockMvc.perform(delete("/api/features/{id}", features.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Features> featuresList = featuresRepository.findAll();
        assertThat(featuresList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Features.class);
        Features features1 = new Features();
        features1.setId(1L);
        Features features2 = new Features();
        features2.setId(features1.getId());
        assertThat(features1).isEqualTo(features2);
        features2.setId(2L);
        assertThat(features1).isNotEqualTo(features2);
        features1.setId(null);
        assertThat(features1).isNotEqualTo(features2);
    }
}
