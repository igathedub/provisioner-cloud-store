package skyy.blue.web.rest;

import skyy.blue.ProvisionercloudApp;
import skyy.blue.domain.AllocatedRange;
import skyy.blue.repository.AllocatedRangeRepository;
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
 * Integration tests for the {@link AllocatedRangeResource} REST controller.
 */
@SpringBootTest(classes = ProvisionercloudApp.class)
public class AllocatedRangeResourceIT {

    private static final String DEFAULT_LOW_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_LOW_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_HIGH_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_HIGH_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private AllocatedRangeRepository allocatedRangeRepository;

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

    private MockMvc restAllocatedRangeMockMvc;

    private AllocatedRange allocatedRange;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AllocatedRangeResource allocatedRangeResource = new AllocatedRangeResource(allocatedRangeRepository);
        this.restAllocatedRangeMockMvc = MockMvcBuilders.standaloneSetup(allocatedRangeResource)
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
    public static AllocatedRange createEntity(EntityManager em) {
        AllocatedRange allocatedRange = new AllocatedRange()
            .lowAddress(DEFAULT_LOW_ADDRESS)
            .highAddress(DEFAULT_HIGH_ADDRESS);
        return allocatedRange;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AllocatedRange createUpdatedEntity(EntityManager em) {
        AllocatedRange allocatedRange = new AllocatedRange()
            .lowAddress(UPDATED_LOW_ADDRESS)
            .highAddress(UPDATED_HIGH_ADDRESS);
        return allocatedRange;
    }

    @BeforeEach
    public void initTest() {
        allocatedRange = createEntity(em);
    }

    @Test
    @Transactional
    public void createAllocatedRange() throws Exception {
        int databaseSizeBeforeCreate = allocatedRangeRepository.findAll().size();

        // Create the AllocatedRange
        restAllocatedRangeMockMvc.perform(post("/api/allocated-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allocatedRange)))
            .andExpect(status().isCreated());

        // Validate the AllocatedRange in the database
        List<AllocatedRange> allocatedRangeList = allocatedRangeRepository.findAll();
        assertThat(allocatedRangeList).hasSize(databaseSizeBeforeCreate + 1);
        AllocatedRange testAllocatedRange = allocatedRangeList.get(allocatedRangeList.size() - 1);
        assertThat(testAllocatedRange.getLowAddress()).isEqualTo(DEFAULT_LOW_ADDRESS);
        assertThat(testAllocatedRange.getHighAddress()).isEqualTo(DEFAULT_HIGH_ADDRESS);
    }

    @Test
    @Transactional
    public void createAllocatedRangeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = allocatedRangeRepository.findAll().size();

        // Create the AllocatedRange with an existing ID
        allocatedRange.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAllocatedRangeMockMvc.perform(post("/api/allocated-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allocatedRange)))
            .andExpect(status().isBadRequest());

        // Validate the AllocatedRange in the database
        List<AllocatedRange> allocatedRangeList = allocatedRangeRepository.findAll();
        assertThat(allocatedRangeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAllocatedRanges() throws Exception {
        // Initialize the database
        allocatedRangeRepository.saveAndFlush(allocatedRange);

        // Get all the allocatedRangeList
        restAllocatedRangeMockMvc.perform(get("/api/allocated-ranges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(allocatedRange.getId().intValue())))
            .andExpect(jsonPath("$.[*].lowAddress").value(hasItem(DEFAULT_LOW_ADDRESS)))
            .andExpect(jsonPath("$.[*].highAddress").value(hasItem(DEFAULT_HIGH_ADDRESS)));
    }
    
    @Test
    @Transactional
    public void getAllocatedRange() throws Exception {
        // Initialize the database
        allocatedRangeRepository.saveAndFlush(allocatedRange);

        // Get the allocatedRange
        restAllocatedRangeMockMvc.perform(get("/api/allocated-ranges/{id}", allocatedRange.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(allocatedRange.getId().intValue()))
            .andExpect(jsonPath("$.lowAddress").value(DEFAULT_LOW_ADDRESS))
            .andExpect(jsonPath("$.highAddress").value(DEFAULT_HIGH_ADDRESS));
    }

    @Test
    @Transactional
    public void getNonExistingAllocatedRange() throws Exception {
        // Get the allocatedRange
        restAllocatedRangeMockMvc.perform(get("/api/allocated-ranges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAllocatedRange() throws Exception {
        // Initialize the database
        allocatedRangeRepository.saveAndFlush(allocatedRange);

        int databaseSizeBeforeUpdate = allocatedRangeRepository.findAll().size();

        // Update the allocatedRange
        AllocatedRange updatedAllocatedRange = allocatedRangeRepository.findById(allocatedRange.getId()).get();
        // Disconnect from session so that the updates on updatedAllocatedRange are not directly saved in db
        em.detach(updatedAllocatedRange);
        updatedAllocatedRange
            .lowAddress(UPDATED_LOW_ADDRESS)
            .highAddress(UPDATED_HIGH_ADDRESS);

        restAllocatedRangeMockMvc.perform(put("/api/allocated-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAllocatedRange)))
            .andExpect(status().isOk());

        // Validate the AllocatedRange in the database
        List<AllocatedRange> allocatedRangeList = allocatedRangeRepository.findAll();
        assertThat(allocatedRangeList).hasSize(databaseSizeBeforeUpdate);
        AllocatedRange testAllocatedRange = allocatedRangeList.get(allocatedRangeList.size() - 1);
        assertThat(testAllocatedRange.getLowAddress()).isEqualTo(UPDATED_LOW_ADDRESS);
        assertThat(testAllocatedRange.getHighAddress()).isEqualTo(UPDATED_HIGH_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingAllocatedRange() throws Exception {
        int databaseSizeBeforeUpdate = allocatedRangeRepository.findAll().size();

        // Create the AllocatedRange

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAllocatedRangeMockMvc.perform(put("/api/allocated-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allocatedRange)))
            .andExpect(status().isBadRequest());

        // Validate the AllocatedRange in the database
        List<AllocatedRange> allocatedRangeList = allocatedRangeRepository.findAll();
        assertThat(allocatedRangeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAllocatedRange() throws Exception {
        // Initialize the database
        allocatedRangeRepository.saveAndFlush(allocatedRange);

        int databaseSizeBeforeDelete = allocatedRangeRepository.findAll().size();

        // Delete the allocatedRange
        restAllocatedRangeMockMvc.perform(delete("/api/allocated-ranges/{id}", allocatedRange.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AllocatedRange> allocatedRangeList = allocatedRangeRepository.findAll();
        assertThat(allocatedRangeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AllocatedRange.class);
        AllocatedRange allocatedRange1 = new AllocatedRange();
        allocatedRange1.setId(1L);
        AllocatedRange allocatedRange2 = new AllocatedRange();
        allocatedRange2.setId(allocatedRange1.getId());
        assertThat(allocatedRange1).isEqualTo(allocatedRange2);
        allocatedRange2.setId(2L);
        assertThat(allocatedRange1).isNotEqualTo(allocatedRange2);
        allocatedRange1.setId(null);
        assertThat(allocatedRange1).isNotEqualTo(allocatedRange2);
    }
}
