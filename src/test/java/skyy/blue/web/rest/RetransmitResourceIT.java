package skyy.blue.web.rest;

import skyy.blue.ProvisionercloudApp;
import skyy.blue.domain.Retransmit;
import skyy.blue.repository.RetransmitRepository;
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
 * Integration tests for the {@link RetransmitResource} REST controller.
 */
@SpringBootTest(classes = ProvisionercloudApp.class)
public class RetransmitResourceIT {

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;

    private static final Integer DEFAULT_INTERVAL = 1;
    private static final Integer UPDATED_INTERVAL = 2;

    @Autowired
    private RetransmitRepository retransmitRepository;

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

    private MockMvc restRetransmitMockMvc;

    private Retransmit retransmit;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RetransmitResource retransmitResource = new RetransmitResource(retransmitRepository);
        this.restRetransmitMockMvc = MockMvcBuilders.standaloneSetup(retransmitResource)
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
    public static Retransmit createEntity(EntityManager em) {
        Retransmit retransmit = new Retransmit()
            .count(DEFAULT_COUNT)
            .interval(DEFAULT_INTERVAL);
        return retransmit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Retransmit createUpdatedEntity(EntityManager em) {
        Retransmit retransmit = new Retransmit()
            .count(UPDATED_COUNT)
            .interval(UPDATED_INTERVAL);
        return retransmit;
    }

    @BeforeEach
    public void initTest() {
        retransmit = createEntity(em);
    }

    @Test
    @Transactional
    public void createRetransmit() throws Exception {
        int databaseSizeBeforeCreate = retransmitRepository.findAll().size();

        // Create the Retransmit
        restRetransmitMockMvc.perform(post("/api/retransmits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(retransmit)))
            .andExpect(status().isCreated());

        // Validate the Retransmit in the database
        List<Retransmit> retransmitList = retransmitRepository.findAll();
        assertThat(retransmitList).hasSize(databaseSizeBeforeCreate + 1);
        Retransmit testRetransmit = retransmitList.get(retransmitList.size() - 1);
        assertThat(testRetransmit.getCount()).isEqualTo(DEFAULT_COUNT);
        assertThat(testRetransmit.getInterval()).isEqualTo(DEFAULT_INTERVAL);
    }

    @Test
    @Transactional
    public void createRetransmitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = retransmitRepository.findAll().size();

        // Create the Retransmit with an existing ID
        retransmit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRetransmitMockMvc.perform(post("/api/retransmits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(retransmit)))
            .andExpect(status().isBadRequest());

        // Validate the Retransmit in the database
        List<Retransmit> retransmitList = retransmitRepository.findAll();
        assertThat(retransmitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRetransmits() throws Exception {
        // Initialize the database
        retransmitRepository.saveAndFlush(retransmit);

        // Get all the retransmitList
        restRetransmitMockMvc.perform(get("/api/retransmits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(retransmit.getId().intValue())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)))
            .andExpect(jsonPath("$.[*].interval").value(hasItem(DEFAULT_INTERVAL)));
    }
    
    @Test
    @Transactional
    public void getRetransmit() throws Exception {
        // Initialize the database
        retransmitRepository.saveAndFlush(retransmit);

        // Get the retransmit
        restRetransmitMockMvc.perform(get("/api/retransmits/{id}", retransmit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(retransmit.getId().intValue()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT))
            .andExpect(jsonPath("$.interval").value(DEFAULT_INTERVAL));
    }

    @Test
    @Transactional
    public void getNonExistingRetransmit() throws Exception {
        // Get the retransmit
        restRetransmitMockMvc.perform(get("/api/retransmits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRetransmit() throws Exception {
        // Initialize the database
        retransmitRepository.saveAndFlush(retransmit);

        int databaseSizeBeforeUpdate = retransmitRepository.findAll().size();

        // Update the retransmit
        Retransmit updatedRetransmit = retransmitRepository.findById(retransmit.getId()).get();
        // Disconnect from session so that the updates on updatedRetransmit are not directly saved in db
        em.detach(updatedRetransmit);
        updatedRetransmit
            .count(UPDATED_COUNT)
            .interval(UPDATED_INTERVAL);

        restRetransmitMockMvc.perform(put("/api/retransmits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRetransmit)))
            .andExpect(status().isOk());

        // Validate the Retransmit in the database
        List<Retransmit> retransmitList = retransmitRepository.findAll();
        assertThat(retransmitList).hasSize(databaseSizeBeforeUpdate);
        Retransmit testRetransmit = retransmitList.get(retransmitList.size() - 1);
        assertThat(testRetransmit.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testRetransmit.getInterval()).isEqualTo(UPDATED_INTERVAL);
    }

    @Test
    @Transactional
    public void updateNonExistingRetransmit() throws Exception {
        int databaseSizeBeforeUpdate = retransmitRepository.findAll().size();

        // Create the Retransmit

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRetransmitMockMvc.perform(put("/api/retransmits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(retransmit)))
            .andExpect(status().isBadRequest());

        // Validate the Retransmit in the database
        List<Retransmit> retransmitList = retransmitRepository.findAll();
        assertThat(retransmitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRetransmit() throws Exception {
        // Initialize the database
        retransmitRepository.saveAndFlush(retransmit);

        int databaseSizeBeforeDelete = retransmitRepository.findAll().size();

        // Delete the retransmit
        restRetransmitMockMvc.perform(delete("/api/retransmits/{id}", retransmit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Retransmit> retransmitList = retransmitRepository.findAll();
        assertThat(retransmitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Retransmit.class);
        Retransmit retransmit1 = new Retransmit();
        retransmit1.setId(1L);
        Retransmit retransmit2 = new Retransmit();
        retransmit2.setId(retransmit1.getId());
        assertThat(retransmit1).isEqualTo(retransmit2);
        retransmit2.setId(2L);
        assertThat(retransmit1).isNotEqualTo(retransmit2);
        retransmit1.setId(null);
        assertThat(retransmit1).isNotEqualTo(retransmit2);
    }
}
