package skyy.blue.web.rest;

import skyy.blue.ProvisionercloudApp;
import skyy.blue.domain.NetworkConfiguration;
import skyy.blue.repository.NetworkConfigurationRepository;
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
 * Integration tests for the {@link NetworkConfigurationResource} REST controller.
 */
@SpringBootTest(classes = ProvisionercloudApp.class)
public class NetworkConfigurationResourceIT {

    private static final String DEFAULT_MESH_UUID = "AAAAAAAAAA";
    private static final String UPDATED_MESH_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_TIMESTAMP = "AAAAAAAAAA";
    private static final String UPDATED_TIMESTAMP = "BBBBBBBBBB";

    private static final Integer DEFAULT_MESH_NAME = 1;
    private static final Integer UPDATED_MESH_NAME = 2;

    @Autowired
    private NetworkConfigurationRepository networkConfigurationRepository;

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

    private MockMvc restNetworkConfigurationMockMvc;

    private NetworkConfiguration networkConfiguration;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NetworkConfigurationResource networkConfigurationResource = new NetworkConfigurationResource(networkConfigurationRepository);
        this.restNetworkConfigurationMockMvc = MockMvcBuilders.standaloneSetup(networkConfigurationResource)
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
    public static NetworkConfiguration createEntity(EntityManager em) {
        NetworkConfiguration networkConfiguration = new NetworkConfiguration()
            .meshUUID(DEFAULT_MESH_UUID)
            .timestamp(DEFAULT_TIMESTAMP)
            .meshName(DEFAULT_MESH_NAME);
        return networkConfiguration;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NetworkConfiguration createUpdatedEntity(EntityManager em) {
        NetworkConfiguration networkConfiguration = new NetworkConfiguration()
            .meshUUID(UPDATED_MESH_UUID)
            .timestamp(UPDATED_TIMESTAMP)
            .meshName(UPDATED_MESH_NAME);
        return networkConfiguration;
    }

    @BeforeEach
    public void initTest() {
        networkConfiguration = createEntity(em);
    }

    @Test
    @Transactional
    public void createNetworkConfiguration() throws Exception {
        int databaseSizeBeforeCreate = networkConfigurationRepository.findAll().size();

        // Create the NetworkConfiguration
        restNetworkConfigurationMockMvc.perform(post("/api/network-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(networkConfiguration)))
            .andExpect(status().isCreated());

        // Validate the NetworkConfiguration in the database
        List<NetworkConfiguration> networkConfigurationList = networkConfigurationRepository.findAll();
        assertThat(networkConfigurationList).hasSize(databaseSizeBeforeCreate + 1);
        NetworkConfiguration testNetworkConfiguration = networkConfigurationList.get(networkConfigurationList.size() - 1);
        assertThat(testNetworkConfiguration.getMeshUUID()).isEqualTo(DEFAULT_MESH_UUID);
        assertThat(testNetworkConfiguration.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testNetworkConfiguration.getMeshName()).isEqualTo(DEFAULT_MESH_NAME);
    }

    @Test
    @Transactional
    public void createNetworkConfigurationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = networkConfigurationRepository.findAll().size();

        // Create the NetworkConfiguration with an existing ID
        networkConfiguration.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNetworkConfigurationMockMvc.perform(post("/api/network-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(networkConfiguration)))
            .andExpect(status().isBadRequest());

        // Validate the NetworkConfiguration in the database
        List<NetworkConfiguration> networkConfigurationList = networkConfigurationRepository.findAll();
        assertThat(networkConfigurationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNetworkConfigurations() throws Exception {
        // Initialize the database
        networkConfigurationRepository.saveAndFlush(networkConfiguration);

        // Get all the networkConfigurationList
        restNetworkConfigurationMockMvc.perform(get("/api/network-configurations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(networkConfiguration.getId().intValue())))
            .andExpect(jsonPath("$.[*].meshUUID").value(hasItem(DEFAULT_MESH_UUID)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)))
            .andExpect(jsonPath("$.[*].meshName").value(hasItem(DEFAULT_MESH_NAME)));
    }
    
    @Test
    @Transactional
    public void getNetworkConfiguration() throws Exception {
        // Initialize the database
        networkConfigurationRepository.saveAndFlush(networkConfiguration);

        // Get the networkConfiguration
        restNetworkConfigurationMockMvc.perform(get("/api/network-configurations/{id}", networkConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(networkConfiguration.getId().intValue()))
            .andExpect(jsonPath("$.meshUUID").value(DEFAULT_MESH_UUID))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP))
            .andExpect(jsonPath("$.meshName").value(DEFAULT_MESH_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingNetworkConfiguration() throws Exception {
        // Get the networkConfiguration
        restNetworkConfigurationMockMvc.perform(get("/api/network-configurations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNetworkConfiguration() throws Exception {
        // Initialize the database
        networkConfigurationRepository.saveAndFlush(networkConfiguration);

        int databaseSizeBeforeUpdate = networkConfigurationRepository.findAll().size();

        // Update the networkConfiguration
        NetworkConfiguration updatedNetworkConfiguration = networkConfigurationRepository.findById(networkConfiguration.getId()).get();
        // Disconnect from session so that the updates on updatedNetworkConfiguration are not directly saved in db
        em.detach(updatedNetworkConfiguration);
        updatedNetworkConfiguration
            .meshUUID(UPDATED_MESH_UUID)
            .timestamp(UPDATED_TIMESTAMP)
            .meshName(UPDATED_MESH_NAME);

        restNetworkConfigurationMockMvc.perform(put("/api/network-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNetworkConfiguration)))
            .andExpect(status().isOk());

        // Validate the NetworkConfiguration in the database
        List<NetworkConfiguration> networkConfigurationList = networkConfigurationRepository.findAll();
        assertThat(networkConfigurationList).hasSize(databaseSizeBeforeUpdate);
        NetworkConfiguration testNetworkConfiguration = networkConfigurationList.get(networkConfigurationList.size() - 1);
        assertThat(testNetworkConfiguration.getMeshUUID()).isEqualTo(UPDATED_MESH_UUID);
        assertThat(testNetworkConfiguration.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testNetworkConfiguration.getMeshName()).isEqualTo(UPDATED_MESH_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingNetworkConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = networkConfigurationRepository.findAll().size();

        // Create the NetworkConfiguration

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNetworkConfigurationMockMvc.perform(put("/api/network-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(networkConfiguration)))
            .andExpect(status().isBadRequest());

        // Validate the NetworkConfiguration in the database
        List<NetworkConfiguration> networkConfigurationList = networkConfigurationRepository.findAll();
        assertThat(networkConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNetworkConfiguration() throws Exception {
        // Initialize the database
        networkConfigurationRepository.saveAndFlush(networkConfiguration);

        int databaseSizeBeforeDelete = networkConfigurationRepository.findAll().size();

        // Delete the networkConfiguration
        restNetworkConfigurationMockMvc.perform(delete("/api/network-configurations/{id}", networkConfiguration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NetworkConfiguration> networkConfigurationList = networkConfigurationRepository.findAll();
        assertThat(networkConfigurationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NetworkConfiguration.class);
        NetworkConfiguration networkConfiguration1 = new NetworkConfiguration();
        networkConfiguration1.setId(1L);
        NetworkConfiguration networkConfiguration2 = new NetworkConfiguration();
        networkConfiguration2.setId(networkConfiguration1.getId());
        assertThat(networkConfiguration1).isEqualTo(networkConfiguration2);
        networkConfiguration2.setId(2L);
        assertThat(networkConfiguration1).isNotEqualTo(networkConfiguration2);
        networkConfiguration1.setId(null);
        assertThat(networkConfiguration1).isNotEqualTo(networkConfiguration2);
    }
}
