package skyy.blue.web.rest;

import skyy.blue.ProvisionercloudApp;
import skyy.blue.domain.NetKey;
import skyy.blue.repository.NetKeyRepository;
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
 * Integration tests for the {@link NetKeyResource} REST controller.
 */
@SpringBootTest(classes = ProvisionercloudApp.class)
public class NetKeyResourceIT {

    private static final Integer DEFAULT_PHASE = 1;
    private static final Integer UPDATED_PHASE = 2;

    private static final String DEFAULT_MIN_SECURITY = "AAAAAAAAAA";
    private static final String UPDATED_MIN_SECURITY = "BBBBBBBBBB";

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_TIMESTAMP = "AAAAAAAAAA";
    private static final String UPDATED_TIMESTAMP = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_INDEX = 1;
    private static final Integer UPDATED_INDEX = 2;

    @Autowired
    private NetKeyRepository netKeyRepository;

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

    private MockMvc restNetKeyMockMvc;

    private NetKey netKey;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NetKeyResource netKeyResource = new NetKeyResource(netKeyRepository);
        this.restNetKeyMockMvc = MockMvcBuilders.standaloneSetup(netKeyResource)
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
    public static NetKey createEntity(EntityManager em) {
        NetKey netKey = new NetKey()
            .phase(DEFAULT_PHASE)
            .minSecurity(DEFAULT_MIN_SECURITY)
            .key(DEFAULT_KEY)
            .timestamp(DEFAULT_TIMESTAMP)
            .name(DEFAULT_NAME)
            .index(DEFAULT_INDEX);
        return netKey;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NetKey createUpdatedEntity(EntityManager em) {
        NetKey netKey = new NetKey()
            .phase(UPDATED_PHASE)
            .minSecurity(UPDATED_MIN_SECURITY)
            .key(UPDATED_KEY)
            .timestamp(UPDATED_TIMESTAMP)
            .name(UPDATED_NAME)
            .index(UPDATED_INDEX);
        return netKey;
    }

    @BeforeEach
    public void initTest() {
        netKey = createEntity(em);
    }

    @Test
    @Transactional
    public void createNetKey() throws Exception {
        int databaseSizeBeforeCreate = netKeyRepository.findAll().size();

        // Create the NetKey
        restNetKeyMockMvc.perform(post("/api/net-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(netKey)))
            .andExpect(status().isCreated());

        // Validate the NetKey in the database
        List<NetKey> netKeyList = netKeyRepository.findAll();
        assertThat(netKeyList).hasSize(databaseSizeBeforeCreate + 1);
        NetKey testNetKey = netKeyList.get(netKeyList.size() - 1);
        assertThat(testNetKey.getPhase()).isEqualTo(DEFAULT_PHASE);
        assertThat(testNetKey.getMinSecurity()).isEqualTo(DEFAULT_MIN_SECURITY);
        assertThat(testNetKey.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testNetKey.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testNetKey.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNetKey.getIndex()).isEqualTo(DEFAULT_INDEX);
    }

    @Test
    @Transactional
    public void createNetKeyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = netKeyRepository.findAll().size();

        // Create the NetKey with an existing ID
        netKey.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNetKeyMockMvc.perform(post("/api/net-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(netKey)))
            .andExpect(status().isBadRequest());

        // Validate the NetKey in the database
        List<NetKey> netKeyList = netKeyRepository.findAll();
        assertThat(netKeyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNetKeys() throws Exception {
        // Initialize the database
        netKeyRepository.saveAndFlush(netKey);

        // Get all the netKeyList
        restNetKeyMockMvc.perform(get("/api/net-keys?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(netKey.getId().intValue())))
            .andExpect(jsonPath("$.[*].phase").value(hasItem(DEFAULT_PHASE)))
            .andExpect(jsonPath("$.[*].minSecurity").value(hasItem(DEFAULT_MIN_SECURITY)))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].index").value(hasItem(DEFAULT_INDEX)));
    }
    
    @Test
    @Transactional
    public void getNetKey() throws Exception {
        // Initialize the database
        netKeyRepository.saveAndFlush(netKey);

        // Get the netKey
        restNetKeyMockMvc.perform(get("/api/net-keys/{id}", netKey.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(netKey.getId().intValue()))
            .andExpect(jsonPath("$.phase").value(DEFAULT_PHASE))
            .andExpect(jsonPath("$.minSecurity").value(DEFAULT_MIN_SECURITY))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.index").value(DEFAULT_INDEX));
    }

    @Test
    @Transactional
    public void getNonExistingNetKey() throws Exception {
        // Get the netKey
        restNetKeyMockMvc.perform(get("/api/net-keys/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNetKey() throws Exception {
        // Initialize the database
        netKeyRepository.saveAndFlush(netKey);

        int databaseSizeBeforeUpdate = netKeyRepository.findAll().size();

        // Update the netKey
        NetKey updatedNetKey = netKeyRepository.findById(netKey.getId()).get();
        // Disconnect from session so that the updates on updatedNetKey are not directly saved in db
        em.detach(updatedNetKey);
        updatedNetKey
            .phase(UPDATED_PHASE)
            .minSecurity(UPDATED_MIN_SECURITY)
            .key(UPDATED_KEY)
            .timestamp(UPDATED_TIMESTAMP)
            .name(UPDATED_NAME)
            .index(UPDATED_INDEX);

        restNetKeyMockMvc.perform(put("/api/net-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNetKey)))
            .andExpect(status().isOk());

        // Validate the NetKey in the database
        List<NetKey> netKeyList = netKeyRepository.findAll();
        assertThat(netKeyList).hasSize(databaseSizeBeforeUpdate);
        NetKey testNetKey = netKeyList.get(netKeyList.size() - 1);
        assertThat(testNetKey.getPhase()).isEqualTo(UPDATED_PHASE);
        assertThat(testNetKey.getMinSecurity()).isEqualTo(UPDATED_MIN_SECURITY);
        assertThat(testNetKey.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testNetKey.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testNetKey.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNetKey.getIndex()).isEqualTo(UPDATED_INDEX);
    }

    @Test
    @Transactional
    public void updateNonExistingNetKey() throws Exception {
        int databaseSizeBeforeUpdate = netKeyRepository.findAll().size();

        // Create the NetKey

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNetKeyMockMvc.perform(put("/api/net-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(netKey)))
            .andExpect(status().isBadRequest());

        // Validate the NetKey in the database
        List<NetKey> netKeyList = netKeyRepository.findAll();
        assertThat(netKeyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNetKey() throws Exception {
        // Initialize the database
        netKeyRepository.saveAndFlush(netKey);

        int databaseSizeBeforeDelete = netKeyRepository.findAll().size();

        // Delete the netKey
        restNetKeyMockMvc.perform(delete("/api/net-keys/{id}", netKey.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NetKey> netKeyList = netKeyRepository.findAll();
        assertThat(netKeyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NetKey.class);
        NetKey netKey1 = new NetKey();
        netKey1.setId(1L);
        NetKey netKey2 = new NetKey();
        netKey2.setId(netKey1.getId());
        assertThat(netKey1).isEqualTo(netKey2);
        netKey2.setId(2L);
        assertThat(netKey1).isNotEqualTo(netKey2);
        netKey1.setId(null);
        assertThat(netKey1).isNotEqualTo(netKey2);
    }
}
