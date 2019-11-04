package skyy.blue.web.rest;

import skyy.blue.ProvisionercloudApp;
import skyy.blue.domain.AppKey;
import skyy.blue.repository.AppKeyRepository;
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
 * Integration tests for the {@link AppKeyResource} REST controller.
 */
@SpringBootTest(classes = ProvisionercloudApp.class)
public class AppKeyResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_BOUND_NET_KEY = 1;
    private static final Integer UPDATED_BOUND_NET_KEY = 2;

    private static final Integer DEFAULT_INDEX = 1;
    private static final Integer UPDATED_INDEX = 2;

    @Autowired
    private AppKeyRepository appKeyRepository;

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

    private MockMvc restAppKeyMockMvc;

    private AppKey appKey;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AppKeyResource appKeyResource = new AppKeyResource(appKeyRepository);
        this.restAppKeyMockMvc = MockMvcBuilders.standaloneSetup(appKeyResource)
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
    public static AppKey createEntity(EntityManager em) {
        AppKey appKey = new AppKey()
            .key(DEFAULT_KEY)
            .name(DEFAULT_NAME)
            .boundNetKey(DEFAULT_BOUND_NET_KEY)
            .index(DEFAULT_INDEX);
        return appKey;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppKey createUpdatedEntity(EntityManager em) {
        AppKey appKey = new AppKey()
            .key(UPDATED_KEY)
            .name(UPDATED_NAME)
            .boundNetKey(UPDATED_BOUND_NET_KEY)
            .index(UPDATED_INDEX);
        return appKey;
    }

    @BeforeEach
    public void initTest() {
        appKey = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppKey() throws Exception {
        int databaseSizeBeforeCreate = appKeyRepository.findAll().size();

        // Create the AppKey
        restAppKeyMockMvc.perform(post("/api/app-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appKey)))
            .andExpect(status().isCreated());

        // Validate the AppKey in the database
        List<AppKey> appKeyList = appKeyRepository.findAll();
        assertThat(appKeyList).hasSize(databaseSizeBeforeCreate + 1);
        AppKey testAppKey = appKeyList.get(appKeyList.size() - 1);
        assertThat(testAppKey.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testAppKey.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAppKey.getBoundNetKey()).isEqualTo(DEFAULT_BOUND_NET_KEY);
        assertThat(testAppKey.getIndex()).isEqualTo(DEFAULT_INDEX);
    }

    @Test
    @Transactional
    public void createAppKeyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appKeyRepository.findAll().size();

        // Create the AppKey with an existing ID
        appKey.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppKeyMockMvc.perform(post("/api/app-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appKey)))
            .andExpect(status().isBadRequest());

        // Validate the AppKey in the database
        List<AppKey> appKeyList = appKeyRepository.findAll();
        assertThat(appKeyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAppKeys() throws Exception {
        // Initialize the database
        appKeyRepository.saveAndFlush(appKey);

        // Get all the appKeyList
        restAppKeyMockMvc.perform(get("/api/app-keys?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appKey.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].boundNetKey").value(hasItem(DEFAULT_BOUND_NET_KEY)))
            .andExpect(jsonPath("$.[*].index").value(hasItem(DEFAULT_INDEX)));
    }
    
    @Test
    @Transactional
    public void getAppKey() throws Exception {
        // Initialize the database
        appKeyRepository.saveAndFlush(appKey);

        // Get the appKey
        restAppKeyMockMvc.perform(get("/api/app-keys/{id}", appKey.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appKey.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.boundNetKey").value(DEFAULT_BOUND_NET_KEY))
            .andExpect(jsonPath("$.index").value(DEFAULT_INDEX));
    }

    @Test
    @Transactional
    public void getNonExistingAppKey() throws Exception {
        // Get the appKey
        restAppKeyMockMvc.perform(get("/api/app-keys/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppKey() throws Exception {
        // Initialize the database
        appKeyRepository.saveAndFlush(appKey);

        int databaseSizeBeforeUpdate = appKeyRepository.findAll().size();

        // Update the appKey
        AppKey updatedAppKey = appKeyRepository.findById(appKey.getId()).get();
        // Disconnect from session so that the updates on updatedAppKey are not directly saved in db
        em.detach(updatedAppKey);
        updatedAppKey
            .key(UPDATED_KEY)
            .name(UPDATED_NAME)
            .boundNetKey(UPDATED_BOUND_NET_KEY)
            .index(UPDATED_INDEX);

        restAppKeyMockMvc.perform(put("/api/app-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAppKey)))
            .andExpect(status().isOk());

        // Validate the AppKey in the database
        List<AppKey> appKeyList = appKeyRepository.findAll();
        assertThat(appKeyList).hasSize(databaseSizeBeforeUpdate);
        AppKey testAppKey = appKeyList.get(appKeyList.size() - 1);
        assertThat(testAppKey.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testAppKey.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAppKey.getBoundNetKey()).isEqualTo(UPDATED_BOUND_NET_KEY);
        assertThat(testAppKey.getIndex()).isEqualTo(UPDATED_INDEX);
    }

    @Test
    @Transactional
    public void updateNonExistingAppKey() throws Exception {
        int databaseSizeBeforeUpdate = appKeyRepository.findAll().size();

        // Create the AppKey

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppKeyMockMvc.perform(put("/api/app-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appKey)))
            .andExpect(status().isBadRequest());

        // Validate the AppKey in the database
        List<AppKey> appKeyList = appKeyRepository.findAll();
        assertThat(appKeyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAppKey() throws Exception {
        // Initialize the database
        appKeyRepository.saveAndFlush(appKey);

        int databaseSizeBeforeDelete = appKeyRepository.findAll().size();

        // Delete the appKey
        restAppKeyMockMvc.perform(delete("/api/app-keys/{id}", appKey.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppKey> appKeyList = appKeyRepository.findAll();
        assertThat(appKeyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppKey.class);
        AppKey appKey1 = new AppKey();
        appKey1.setId(1L);
        AppKey appKey2 = new AppKey();
        appKey2.setId(appKey1.getId());
        assertThat(appKey1).isEqualTo(appKey2);
        appKey2.setId(2L);
        assertThat(appKey1).isNotEqualTo(appKey2);
        appKey1.setId(null);
        assertThat(appKey1).isNotEqualTo(appKey2);
    }
}
