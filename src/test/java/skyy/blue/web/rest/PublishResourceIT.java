package skyy.blue.web.rest;

import skyy.blue.ProvisionercloudApp;
import skyy.blue.domain.Publish;
import skyy.blue.repository.PublishRepository;
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
 * Integration tests for the {@link PublishResource} REST controller.
 */
@SpringBootTest(classes = ProvisionercloudApp.class)
public class PublishResourceIT {

    private static final Integer DEFAULT_INDEX = 1;
    private static final Integer UPDATED_INDEX = 2;

    private static final Integer DEFAULT_CREDENTIALS = 1;
    private static final Integer UPDATED_CREDENTIALS = 2;

    private static final Integer DEFAULT_TTL = 1;
    private static final Integer UPDATED_TTL = 2;

    private static final Integer DEFAULT_PERIOD = 1;
    private static final Integer UPDATED_PERIOD = 2;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private PublishRepository publishRepository;

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

    private MockMvc restPublishMockMvc;

    private Publish publish;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PublishResource publishResource = new PublishResource(publishRepository);
        this.restPublishMockMvc = MockMvcBuilders.standaloneSetup(publishResource)
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
    public static Publish createEntity(EntityManager em) {
        Publish publish = new Publish()
            .index(DEFAULT_INDEX)
            .credentials(DEFAULT_CREDENTIALS)
            .ttl(DEFAULT_TTL)
            .period(DEFAULT_PERIOD)
            .address(DEFAULT_ADDRESS);
        return publish;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Publish createUpdatedEntity(EntityManager em) {
        Publish publish = new Publish()
            .index(UPDATED_INDEX)
            .credentials(UPDATED_CREDENTIALS)
            .ttl(UPDATED_TTL)
            .period(UPDATED_PERIOD)
            .address(UPDATED_ADDRESS);
        return publish;
    }

    @BeforeEach
    public void initTest() {
        publish = createEntity(em);
    }

    @Test
    @Transactional
    public void createPublish() throws Exception {
        int databaseSizeBeforeCreate = publishRepository.findAll().size();

        // Create the Publish
        restPublishMockMvc.perform(post("/api/publishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publish)))
            .andExpect(status().isCreated());

        // Validate the Publish in the database
        List<Publish> publishList = publishRepository.findAll();
        assertThat(publishList).hasSize(databaseSizeBeforeCreate + 1);
        Publish testPublish = publishList.get(publishList.size() - 1);
        assertThat(testPublish.getIndex()).isEqualTo(DEFAULT_INDEX);
        assertThat(testPublish.getCredentials()).isEqualTo(DEFAULT_CREDENTIALS);
        assertThat(testPublish.getTtl()).isEqualTo(DEFAULT_TTL);
        assertThat(testPublish.getPeriod()).isEqualTo(DEFAULT_PERIOD);
        assertThat(testPublish.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void createPublishWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = publishRepository.findAll().size();

        // Create the Publish with an existing ID
        publish.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublishMockMvc.perform(post("/api/publishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publish)))
            .andExpect(status().isBadRequest());

        // Validate the Publish in the database
        List<Publish> publishList = publishRepository.findAll();
        assertThat(publishList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPublishes() throws Exception {
        // Initialize the database
        publishRepository.saveAndFlush(publish);

        // Get all the publishList
        restPublishMockMvc.perform(get("/api/publishes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publish.getId().intValue())))
            .andExpect(jsonPath("$.[*].index").value(hasItem(DEFAULT_INDEX)))
            .andExpect(jsonPath("$.[*].credentials").value(hasItem(DEFAULT_CREDENTIALS)))
            .andExpect(jsonPath("$.[*].ttl").value(hasItem(DEFAULT_TTL)))
            .andExpect(jsonPath("$.[*].period").value(hasItem(DEFAULT_PERIOD)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }
    
    @Test
    @Transactional
    public void getPublish() throws Exception {
        // Initialize the database
        publishRepository.saveAndFlush(publish);

        // Get the publish
        restPublishMockMvc.perform(get("/api/publishes/{id}", publish.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(publish.getId().intValue()))
            .andExpect(jsonPath("$.index").value(DEFAULT_INDEX))
            .andExpect(jsonPath("$.credentials").value(DEFAULT_CREDENTIALS))
            .andExpect(jsonPath("$.ttl").value(DEFAULT_TTL))
            .andExpect(jsonPath("$.period").value(DEFAULT_PERIOD))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }

    @Test
    @Transactional
    public void getNonExistingPublish() throws Exception {
        // Get the publish
        restPublishMockMvc.perform(get("/api/publishes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePublish() throws Exception {
        // Initialize the database
        publishRepository.saveAndFlush(publish);

        int databaseSizeBeforeUpdate = publishRepository.findAll().size();

        // Update the publish
        Publish updatedPublish = publishRepository.findById(publish.getId()).get();
        // Disconnect from session so that the updates on updatedPublish are not directly saved in db
        em.detach(updatedPublish);
        updatedPublish
            .index(UPDATED_INDEX)
            .credentials(UPDATED_CREDENTIALS)
            .ttl(UPDATED_TTL)
            .period(UPDATED_PERIOD)
            .address(UPDATED_ADDRESS);

        restPublishMockMvc.perform(put("/api/publishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPublish)))
            .andExpect(status().isOk());

        // Validate the Publish in the database
        List<Publish> publishList = publishRepository.findAll();
        assertThat(publishList).hasSize(databaseSizeBeforeUpdate);
        Publish testPublish = publishList.get(publishList.size() - 1);
        assertThat(testPublish.getIndex()).isEqualTo(UPDATED_INDEX);
        assertThat(testPublish.getCredentials()).isEqualTo(UPDATED_CREDENTIALS);
        assertThat(testPublish.getTtl()).isEqualTo(UPDATED_TTL);
        assertThat(testPublish.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testPublish.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingPublish() throws Exception {
        int databaseSizeBeforeUpdate = publishRepository.findAll().size();

        // Create the Publish

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublishMockMvc.perform(put("/api/publishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publish)))
            .andExpect(status().isBadRequest());

        // Validate the Publish in the database
        List<Publish> publishList = publishRepository.findAll();
        assertThat(publishList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePublish() throws Exception {
        // Initialize the database
        publishRepository.saveAndFlush(publish);

        int databaseSizeBeforeDelete = publishRepository.findAll().size();

        // Delete the publish
        restPublishMockMvc.perform(delete("/api/publishes/{id}", publish.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Publish> publishList = publishRepository.findAll();
        assertThat(publishList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Publish.class);
        Publish publish1 = new Publish();
        publish1.setId(1L);
        Publish publish2 = new Publish();
        publish2.setId(publish1.getId());
        assertThat(publish1).isEqualTo(publish2);
        publish2.setId(2L);
        assertThat(publish1).isNotEqualTo(publish2);
        publish1.setId(null);
        assertThat(publish1).isNotEqualTo(publish2);
    }
}
