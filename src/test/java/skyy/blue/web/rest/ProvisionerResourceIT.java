package skyy.blue.web.rest;

import skyy.blue.ProvisionercloudApp;
import skyy.blue.domain.Provisioner;
import skyy.blue.repository.ProvisionerRepository;
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
 * Integration tests for the {@link ProvisionerResource} REST controller.
 */
@SpringBootTest(classes = ProvisionercloudApp.class)
public class ProvisionerResourceIT {

    private static final String DEFAULT_U_UID = "AAAAAAAAAA";
    private static final String UPDATED_U_UID = "BBBBBBBBBB";

    private static final String DEFAULT_PROVISIONER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROVISIONER_NAME = "BBBBBBBBBB";

    @Autowired
    private ProvisionerRepository provisionerRepository;

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

    private MockMvc restProvisionerMockMvc;

    private Provisioner provisioner;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProvisionerResource provisionerResource = new ProvisionerResource(provisionerRepository);
        this.restProvisionerMockMvc = MockMvcBuilders.standaloneSetup(provisionerResource)
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
    public static Provisioner createEntity(EntityManager em) {
        Provisioner provisioner = new Provisioner()
            .uUID(DEFAULT_U_UID)
            .provisionerName(DEFAULT_PROVISIONER_NAME);
        return provisioner;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Provisioner createUpdatedEntity(EntityManager em) {
        Provisioner provisioner = new Provisioner()
            .uUID(UPDATED_U_UID)
            .provisionerName(UPDATED_PROVISIONER_NAME);
        return provisioner;
    }

    @BeforeEach
    public void initTest() {
        provisioner = createEntity(em);
    }

    @Test
    @Transactional
    public void createProvisioner() throws Exception {
        int databaseSizeBeforeCreate = provisionerRepository.findAll().size();

        // Create the Provisioner
        restProvisionerMockMvc.perform(post("/api/provisioners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(provisioner)))
            .andExpect(status().isCreated());

        // Validate the Provisioner in the database
        List<Provisioner> provisionerList = provisionerRepository.findAll();
        assertThat(provisionerList).hasSize(databaseSizeBeforeCreate + 1);
        Provisioner testProvisioner = provisionerList.get(provisionerList.size() - 1);
        assertThat(testProvisioner.getuUID()).isEqualTo(DEFAULT_U_UID);
        assertThat(testProvisioner.getProvisionerName()).isEqualTo(DEFAULT_PROVISIONER_NAME);
    }

    @Test
    @Transactional
    public void createProvisionerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = provisionerRepository.findAll().size();

        // Create the Provisioner with an existing ID
        provisioner.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProvisionerMockMvc.perform(post("/api/provisioners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(provisioner)))
            .andExpect(status().isBadRequest());

        // Validate the Provisioner in the database
        List<Provisioner> provisionerList = provisionerRepository.findAll();
        assertThat(provisionerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProvisioners() throws Exception {
        // Initialize the database
        provisionerRepository.saveAndFlush(provisioner);

        // Get all the provisionerList
        restProvisionerMockMvc.perform(get("/api/provisioners?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(provisioner.getId().intValue())))
            .andExpect(jsonPath("$.[*].uUID").value(hasItem(DEFAULT_U_UID)))
            .andExpect(jsonPath("$.[*].provisionerName").value(hasItem(DEFAULT_PROVISIONER_NAME)));
    }
    
    @Test
    @Transactional
    public void getProvisioner() throws Exception {
        // Initialize the database
        provisionerRepository.saveAndFlush(provisioner);

        // Get the provisioner
        restProvisionerMockMvc.perform(get("/api/provisioners/{id}", provisioner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(provisioner.getId().intValue()))
            .andExpect(jsonPath("$.uUID").value(DEFAULT_U_UID))
            .andExpect(jsonPath("$.provisionerName").value(DEFAULT_PROVISIONER_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingProvisioner() throws Exception {
        // Get the provisioner
        restProvisionerMockMvc.perform(get("/api/provisioners/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProvisioner() throws Exception {
        // Initialize the database
        provisionerRepository.saveAndFlush(provisioner);

        int databaseSizeBeforeUpdate = provisionerRepository.findAll().size();

        // Update the provisioner
        Provisioner updatedProvisioner = provisionerRepository.findById(provisioner.getId()).get();
        // Disconnect from session so that the updates on updatedProvisioner are not directly saved in db
        em.detach(updatedProvisioner);
        updatedProvisioner
            .uUID(UPDATED_U_UID)
            .provisionerName(UPDATED_PROVISIONER_NAME);

        restProvisionerMockMvc.perform(put("/api/provisioners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProvisioner)))
            .andExpect(status().isOk());

        // Validate the Provisioner in the database
        List<Provisioner> provisionerList = provisionerRepository.findAll();
        assertThat(provisionerList).hasSize(databaseSizeBeforeUpdate);
        Provisioner testProvisioner = provisionerList.get(provisionerList.size() - 1);
        assertThat(testProvisioner.getuUID()).isEqualTo(UPDATED_U_UID);
        assertThat(testProvisioner.getProvisionerName()).isEqualTo(UPDATED_PROVISIONER_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingProvisioner() throws Exception {
        int databaseSizeBeforeUpdate = provisionerRepository.findAll().size();

        // Create the Provisioner

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProvisionerMockMvc.perform(put("/api/provisioners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(provisioner)))
            .andExpect(status().isBadRequest());

        // Validate the Provisioner in the database
        List<Provisioner> provisionerList = provisionerRepository.findAll();
        assertThat(provisionerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProvisioner() throws Exception {
        // Initialize the database
        provisionerRepository.saveAndFlush(provisioner);

        int databaseSizeBeforeDelete = provisionerRepository.findAll().size();

        // Delete the provisioner
        restProvisionerMockMvc.perform(delete("/api/provisioners/{id}", provisioner.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Provisioner> provisionerList = provisionerRepository.findAll();
        assertThat(provisionerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Provisioner.class);
        Provisioner provisioner1 = new Provisioner();
        provisioner1.setId(1L);
        Provisioner provisioner2 = new Provisioner();
        provisioner2.setId(provisioner1.getId());
        assertThat(provisioner1).isEqualTo(provisioner2);
        provisioner2.setId(2L);
        assertThat(provisioner1).isNotEqualTo(provisioner2);
        provisioner1.setId(null);
        assertThat(provisioner1).isNotEqualTo(provisioner2);
    }
}
