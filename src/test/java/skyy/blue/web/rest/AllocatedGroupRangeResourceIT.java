package skyy.blue.web.rest;

import skyy.blue.ProvisionercloudApp;
import skyy.blue.domain.AllocatedGroupRange;
import skyy.blue.repository.AllocatedGroupRangeRepository;
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
 * Integration tests for the {@link AllocatedGroupRangeResource} REST controller.
 */
@SpringBootTest(classes = ProvisionercloudApp.class)
public class AllocatedGroupRangeResourceIT {

    private static final String DEFAULT_LOW_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_LOW_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_HIGH_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_HIGH_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private AllocatedGroupRangeRepository allocatedGroupRangeRepository;

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

    private MockMvc restAllocatedGroupRangeMockMvc;

    private AllocatedGroupRange allocatedGroupRange;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AllocatedGroupRangeResource allocatedGroupRangeResource = new AllocatedGroupRangeResource(allocatedGroupRangeRepository);
        this.restAllocatedGroupRangeMockMvc = MockMvcBuilders.standaloneSetup(allocatedGroupRangeResource)
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
    public static AllocatedGroupRange createEntity(EntityManager em) {
        AllocatedGroupRange allocatedGroupRange = new AllocatedGroupRange()
            .lowAddress(DEFAULT_LOW_ADDRESS)
            .highAddress(DEFAULT_HIGH_ADDRESS);
        return allocatedGroupRange;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AllocatedGroupRange createUpdatedEntity(EntityManager em) {
        AllocatedGroupRange allocatedGroupRange = new AllocatedGroupRange()
            .lowAddress(UPDATED_LOW_ADDRESS)
            .highAddress(UPDATED_HIGH_ADDRESS);
        return allocatedGroupRange;
    }

    @BeforeEach
    public void initTest() {
        allocatedGroupRange = createEntity(em);
    }

    @Test
    @Transactional
    public void createAllocatedGroupRange() throws Exception {
        int databaseSizeBeforeCreate = allocatedGroupRangeRepository.findAll().size();

        // Create the AllocatedGroupRange
        restAllocatedGroupRangeMockMvc.perform(post("/api/allocated-group-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allocatedGroupRange)))
            .andExpect(status().isCreated());

        // Validate the AllocatedGroupRange in the database
        List<AllocatedGroupRange> allocatedGroupRangeList = allocatedGroupRangeRepository.findAll();
        assertThat(allocatedGroupRangeList).hasSize(databaseSizeBeforeCreate + 1);
        AllocatedGroupRange testAllocatedGroupRange = allocatedGroupRangeList.get(allocatedGroupRangeList.size() - 1);
        assertThat(testAllocatedGroupRange.getLowAddress()).isEqualTo(DEFAULT_LOW_ADDRESS);
        assertThat(testAllocatedGroupRange.getHighAddress()).isEqualTo(DEFAULT_HIGH_ADDRESS);
    }

    @Test
    @Transactional
    public void createAllocatedGroupRangeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = allocatedGroupRangeRepository.findAll().size();

        // Create the AllocatedGroupRange with an existing ID
        allocatedGroupRange.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAllocatedGroupRangeMockMvc.perform(post("/api/allocated-group-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allocatedGroupRange)))
            .andExpect(status().isBadRequest());

        // Validate the AllocatedGroupRange in the database
        List<AllocatedGroupRange> allocatedGroupRangeList = allocatedGroupRangeRepository.findAll();
        assertThat(allocatedGroupRangeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAllocatedGroupRanges() throws Exception {
        // Initialize the database
        allocatedGroupRangeRepository.saveAndFlush(allocatedGroupRange);

        // Get all the allocatedGroupRangeList
        restAllocatedGroupRangeMockMvc.perform(get("/api/allocated-group-ranges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(allocatedGroupRange.getId().intValue())))
            .andExpect(jsonPath("$.[*].lowAddress").value(hasItem(DEFAULT_LOW_ADDRESS)))
            .andExpect(jsonPath("$.[*].highAddress").value(hasItem(DEFAULT_HIGH_ADDRESS)));
    }
    
    @Test
    @Transactional
    public void getAllocatedGroupRange() throws Exception {
        // Initialize the database
        allocatedGroupRangeRepository.saveAndFlush(allocatedGroupRange);

        // Get the allocatedGroupRange
        restAllocatedGroupRangeMockMvc.perform(get("/api/allocated-group-ranges/{id}", allocatedGroupRange.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(allocatedGroupRange.getId().intValue()))
            .andExpect(jsonPath("$.lowAddress").value(DEFAULT_LOW_ADDRESS))
            .andExpect(jsonPath("$.highAddress").value(DEFAULT_HIGH_ADDRESS));
    }

    @Test
    @Transactional
    public void getNonExistingAllocatedGroupRange() throws Exception {
        // Get the allocatedGroupRange
        restAllocatedGroupRangeMockMvc.perform(get("/api/allocated-group-ranges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAllocatedGroupRange() throws Exception {
        // Initialize the database
        allocatedGroupRangeRepository.saveAndFlush(allocatedGroupRange);

        int databaseSizeBeforeUpdate = allocatedGroupRangeRepository.findAll().size();

        // Update the allocatedGroupRange
        AllocatedGroupRange updatedAllocatedGroupRange = allocatedGroupRangeRepository.findById(allocatedGroupRange.getId()).get();
        // Disconnect from session so that the updates on updatedAllocatedGroupRange are not directly saved in db
        em.detach(updatedAllocatedGroupRange);
        updatedAllocatedGroupRange
            .lowAddress(UPDATED_LOW_ADDRESS)
            .highAddress(UPDATED_HIGH_ADDRESS);

        restAllocatedGroupRangeMockMvc.perform(put("/api/allocated-group-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAllocatedGroupRange)))
            .andExpect(status().isOk());

        // Validate the AllocatedGroupRange in the database
        List<AllocatedGroupRange> allocatedGroupRangeList = allocatedGroupRangeRepository.findAll();
        assertThat(allocatedGroupRangeList).hasSize(databaseSizeBeforeUpdate);
        AllocatedGroupRange testAllocatedGroupRange = allocatedGroupRangeList.get(allocatedGroupRangeList.size() - 1);
        assertThat(testAllocatedGroupRange.getLowAddress()).isEqualTo(UPDATED_LOW_ADDRESS);
        assertThat(testAllocatedGroupRange.getHighAddress()).isEqualTo(UPDATED_HIGH_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingAllocatedGroupRange() throws Exception {
        int databaseSizeBeforeUpdate = allocatedGroupRangeRepository.findAll().size();

        // Create the AllocatedGroupRange

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAllocatedGroupRangeMockMvc.perform(put("/api/allocated-group-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allocatedGroupRange)))
            .andExpect(status().isBadRequest());

        // Validate the AllocatedGroupRange in the database
        List<AllocatedGroupRange> allocatedGroupRangeList = allocatedGroupRangeRepository.findAll();
        assertThat(allocatedGroupRangeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAllocatedGroupRange() throws Exception {
        // Initialize the database
        allocatedGroupRangeRepository.saveAndFlush(allocatedGroupRange);

        int databaseSizeBeforeDelete = allocatedGroupRangeRepository.findAll().size();

        // Delete the allocatedGroupRange
        restAllocatedGroupRangeMockMvc.perform(delete("/api/allocated-group-ranges/{id}", allocatedGroupRange.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AllocatedGroupRange> allocatedGroupRangeList = allocatedGroupRangeRepository.findAll();
        assertThat(allocatedGroupRangeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AllocatedGroupRange.class);
        AllocatedGroupRange allocatedGroupRange1 = new AllocatedGroupRange();
        allocatedGroupRange1.setId(1L);
        AllocatedGroupRange allocatedGroupRange2 = new AllocatedGroupRange();
        allocatedGroupRange2.setId(allocatedGroupRange1.getId());
        assertThat(allocatedGroupRange1).isEqualTo(allocatedGroupRange2);
        allocatedGroupRange2.setId(2L);
        assertThat(allocatedGroupRange1).isNotEqualTo(allocatedGroupRange2);
        allocatedGroupRange1.setId(null);
        assertThat(allocatedGroupRange1).isNotEqualTo(allocatedGroupRange2);
    }
}
