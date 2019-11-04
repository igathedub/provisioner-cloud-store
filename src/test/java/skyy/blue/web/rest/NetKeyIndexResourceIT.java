package skyy.blue.web.rest;

import skyy.blue.ProvisionercloudApp;
import skyy.blue.domain.NetKeyIndex;
import skyy.blue.repository.NetKeyIndexRepository;
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
 * Integration tests for the {@link NetKeyIndexResource} REST controller.
 */
@SpringBootTest(classes = ProvisionercloudApp.class)
public class NetKeyIndexResourceIT {

    private static final Integer DEFAULT_INDEX = 1;
    private static final Integer UPDATED_INDEX = 2;

    private static final Boolean DEFAULT_UPDATED = false;
    private static final Boolean UPDATED_UPDATED = true;

    @Autowired
    private NetKeyIndexRepository netKeyIndexRepository;

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

    private MockMvc restNetKeyIndexMockMvc;

    private NetKeyIndex netKeyIndex;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NetKeyIndexResource netKeyIndexResource = new NetKeyIndexResource(netKeyIndexRepository);
        this.restNetKeyIndexMockMvc = MockMvcBuilders.standaloneSetup(netKeyIndexResource)
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
    public static NetKeyIndex createEntity(EntityManager em) {
        NetKeyIndex netKeyIndex = new NetKeyIndex()
            .index(DEFAULT_INDEX)
            .updated(DEFAULT_UPDATED);
        return netKeyIndex;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NetKeyIndex createUpdatedEntity(EntityManager em) {
        NetKeyIndex netKeyIndex = new NetKeyIndex()
            .index(UPDATED_INDEX)
            .updated(UPDATED_UPDATED);
        return netKeyIndex;
    }

    @BeforeEach
    public void initTest() {
        netKeyIndex = createEntity(em);
    }

    @Test
    @Transactional
    public void createNetKeyIndex() throws Exception {
        int databaseSizeBeforeCreate = netKeyIndexRepository.findAll().size();

        // Create the NetKeyIndex
        restNetKeyIndexMockMvc.perform(post("/api/net-key-indices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(netKeyIndex)))
            .andExpect(status().isCreated());

        // Validate the NetKeyIndex in the database
        List<NetKeyIndex> netKeyIndexList = netKeyIndexRepository.findAll();
        assertThat(netKeyIndexList).hasSize(databaseSizeBeforeCreate + 1);
        NetKeyIndex testNetKeyIndex = netKeyIndexList.get(netKeyIndexList.size() - 1);
        assertThat(testNetKeyIndex.getIndex()).isEqualTo(DEFAULT_INDEX);
        assertThat(testNetKeyIndex.isUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createNetKeyIndexWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = netKeyIndexRepository.findAll().size();

        // Create the NetKeyIndex with an existing ID
        netKeyIndex.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNetKeyIndexMockMvc.perform(post("/api/net-key-indices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(netKeyIndex)))
            .andExpect(status().isBadRequest());

        // Validate the NetKeyIndex in the database
        List<NetKeyIndex> netKeyIndexList = netKeyIndexRepository.findAll();
        assertThat(netKeyIndexList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNetKeyIndices() throws Exception {
        // Initialize the database
        netKeyIndexRepository.saveAndFlush(netKeyIndex);

        // Get all the netKeyIndexList
        restNetKeyIndexMockMvc.perform(get("/api/net-key-indices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(netKeyIndex.getId().intValue())))
            .andExpect(jsonPath("$.[*].index").value(hasItem(DEFAULT_INDEX)))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getNetKeyIndex() throws Exception {
        // Initialize the database
        netKeyIndexRepository.saveAndFlush(netKeyIndex);

        // Get the netKeyIndex
        restNetKeyIndexMockMvc.perform(get("/api/net-key-indices/{id}", netKeyIndex.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(netKeyIndex.getId().intValue()))
            .andExpect(jsonPath("$.index").value(DEFAULT_INDEX))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingNetKeyIndex() throws Exception {
        // Get the netKeyIndex
        restNetKeyIndexMockMvc.perform(get("/api/net-key-indices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNetKeyIndex() throws Exception {
        // Initialize the database
        netKeyIndexRepository.saveAndFlush(netKeyIndex);

        int databaseSizeBeforeUpdate = netKeyIndexRepository.findAll().size();

        // Update the netKeyIndex
        NetKeyIndex updatedNetKeyIndex = netKeyIndexRepository.findById(netKeyIndex.getId()).get();
        // Disconnect from session so that the updates on updatedNetKeyIndex are not directly saved in db
        em.detach(updatedNetKeyIndex);
        updatedNetKeyIndex
            .index(UPDATED_INDEX)
            .updated(UPDATED_UPDATED);

        restNetKeyIndexMockMvc.perform(put("/api/net-key-indices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNetKeyIndex)))
            .andExpect(status().isOk());

        // Validate the NetKeyIndex in the database
        List<NetKeyIndex> netKeyIndexList = netKeyIndexRepository.findAll();
        assertThat(netKeyIndexList).hasSize(databaseSizeBeforeUpdate);
        NetKeyIndex testNetKeyIndex = netKeyIndexList.get(netKeyIndexList.size() - 1);
        assertThat(testNetKeyIndex.getIndex()).isEqualTo(UPDATED_INDEX);
        assertThat(testNetKeyIndex.isUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingNetKeyIndex() throws Exception {
        int databaseSizeBeforeUpdate = netKeyIndexRepository.findAll().size();

        // Create the NetKeyIndex

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNetKeyIndexMockMvc.perform(put("/api/net-key-indices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(netKeyIndex)))
            .andExpect(status().isBadRequest());

        // Validate the NetKeyIndex in the database
        List<NetKeyIndex> netKeyIndexList = netKeyIndexRepository.findAll();
        assertThat(netKeyIndexList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNetKeyIndex() throws Exception {
        // Initialize the database
        netKeyIndexRepository.saveAndFlush(netKeyIndex);

        int databaseSizeBeforeDelete = netKeyIndexRepository.findAll().size();

        // Delete the netKeyIndex
        restNetKeyIndexMockMvc.perform(delete("/api/net-key-indices/{id}", netKeyIndex.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NetKeyIndex> netKeyIndexList = netKeyIndexRepository.findAll();
        assertThat(netKeyIndexList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NetKeyIndex.class);
        NetKeyIndex netKeyIndex1 = new NetKeyIndex();
        netKeyIndex1.setId(1L);
        NetKeyIndex netKeyIndex2 = new NetKeyIndex();
        netKeyIndex2.setId(netKeyIndex1.getId());
        assertThat(netKeyIndex1).isEqualTo(netKeyIndex2);
        netKeyIndex2.setId(2L);
        assertThat(netKeyIndex1).isNotEqualTo(netKeyIndex2);
        netKeyIndex1.setId(null);
        assertThat(netKeyIndex1).isNotEqualTo(netKeyIndex2);
    }
}
