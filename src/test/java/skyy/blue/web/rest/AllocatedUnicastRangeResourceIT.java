package skyy.blue.web.rest;

import skyy.blue.ProvisionercloudApp;
import skyy.blue.domain.AllocatedUnicastRange;
import skyy.blue.repository.AllocatedUnicastRangeRepository;
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
 * Integration tests for the {@link AllocatedUnicastRangeResource} REST controller.
 */
@SpringBootTest(classes = ProvisionercloudApp.class)
public class AllocatedUnicastRangeResourceIT {

    private static final String DEFAULT_LOW_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_LOW_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_HIGH_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_HIGH_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private AllocatedUnicastRangeRepository allocatedUnicastRangeRepository;

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

    private MockMvc restAllocatedUnicastRangeMockMvc;

    private AllocatedUnicastRange allocatedUnicastRange;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AllocatedUnicastRangeResource allocatedUnicastRangeResource = new AllocatedUnicastRangeResource(allocatedUnicastRangeRepository);
        this.restAllocatedUnicastRangeMockMvc = MockMvcBuilders.standaloneSetup(allocatedUnicastRangeResource)
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
    public static AllocatedUnicastRange createEntity(EntityManager em) {
        AllocatedUnicastRange allocatedUnicastRange = new AllocatedUnicastRange()
            .lowAddress(DEFAULT_LOW_ADDRESS)
            .highAddress(DEFAULT_HIGH_ADDRESS);
        return allocatedUnicastRange;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AllocatedUnicastRange createUpdatedEntity(EntityManager em) {
        AllocatedUnicastRange allocatedUnicastRange = new AllocatedUnicastRange()
            .lowAddress(UPDATED_LOW_ADDRESS)
            .highAddress(UPDATED_HIGH_ADDRESS);
        return allocatedUnicastRange;
    }

    @BeforeEach
    public void initTest() {
        allocatedUnicastRange = createEntity(em);
    }

    @Test
    @Transactional
    public void createAllocatedUnicastRange() throws Exception {
        int databaseSizeBeforeCreate = allocatedUnicastRangeRepository.findAll().size();

        // Create the AllocatedUnicastRange
        restAllocatedUnicastRangeMockMvc.perform(post("/api/allocated-unicast-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allocatedUnicastRange)))
            .andExpect(status().isCreated());

        // Validate the AllocatedUnicastRange in the database
        List<AllocatedUnicastRange> allocatedUnicastRangeList = allocatedUnicastRangeRepository.findAll();
        assertThat(allocatedUnicastRangeList).hasSize(databaseSizeBeforeCreate + 1);
        AllocatedUnicastRange testAllocatedUnicastRange = allocatedUnicastRangeList.get(allocatedUnicastRangeList.size() - 1);
        assertThat(testAllocatedUnicastRange.getLowAddress()).isEqualTo(DEFAULT_LOW_ADDRESS);
        assertThat(testAllocatedUnicastRange.getHighAddress()).isEqualTo(DEFAULT_HIGH_ADDRESS);
    }

    @Test
    @Transactional
    public void createAllocatedUnicastRangeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = allocatedUnicastRangeRepository.findAll().size();

        // Create the AllocatedUnicastRange with an existing ID
        allocatedUnicastRange.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAllocatedUnicastRangeMockMvc.perform(post("/api/allocated-unicast-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allocatedUnicastRange)))
            .andExpect(status().isBadRequest());

        // Validate the AllocatedUnicastRange in the database
        List<AllocatedUnicastRange> allocatedUnicastRangeList = allocatedUnicastRangeRepository.findAll();
        assertThat(allocatedUnicastRangeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAllocatedUnicastRanges() throws Exception {
        // Initialize the database
        allocatedUnicastRangeRepository.saveAndFlush(allocatedUnicastRange);

        // Get all the allocatedUnicastRangeList
        restAllocatedUnicastRangeMockMvc.perform(get("/api/allocated-unicast-ranges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(allocatedUnicastRange.getId().intValue())))
            .andExpect(jsonPath("$.[*].lowAddress").value(hasItem(DEFAULT_LOW_ADDRESS)))
            .andExpect(jsonPath("$.[*].highAddress").value(hasItem(DEFAULT_HIGH_ADDRESS)));
    }
    
    @Test
    @Transactional
    public void getAllocatedUnicastRange() throws Exception {
        // Initialize the database
        allocatedUnicastRangeRepository.saveAndFlush(allocatedUnicastRange);

        // Get the allocatedUnicastRange
        restAllocatedUnicastRangeMockMvc.perform(get("/api/allocated-unicast-ranges/{id}", allocatedUnicastRange.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(allocatedUnicastRange.getId().intValue()))
            .andExpect(jsonPath("$.lowAddress").value(DEFAULT_LOW_ADDRESS))
            .andExpect(jsonPath("$.highAddress").value(DEFAULT_HIGH_ADDRESS));
    }

    @Test
    @Transactional
    public void getNonExistingAllocatedUnicastRange() throws Exception {
        // Get the allocatedUnicastRange
        restAllocatedUnicastRangeMockMvc.perform(get("/api/allocated-unicast-ranges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAllocatedUnicastRange() throws Exception {
        // Initialize the database
        allocatedUnicastRangeRepository.saveAndFlush(allocatedUnicastRange);

        int databaseSizeBeforeUpdate = allocatedUnicastRangeRepository.findAll().size();

        // Update the allocatedUnicastRange
        AllocatedUnicastRange updatedAllocatedUnicastRange = allocatedUnicastRangeRepository.findById(allocatedUnicastRange.getId()).get();
        // Disconnect from session so that the updates on updatedAllocatedUnicastRange are not directly saved in db
        em.detach(updatedAllocatedUnicastRange);
        updatedAllocatedUnicastRange
            .lowAddress(UPDATED_LOW_ADDRESS)
            .highAddress(UPDATED_HIGH_ADDRESS);

        restAllocatedUnicastRangeMockMvc.perform(put("/api/allocated-unicast-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAllocatedUnicastRange)))
            .andExpect(status().isOk());

        // Validate the AllocatedUnicastRange in the database
        List<AllocatedUnicastRange> allocatedUnicastRangeList = allocatedUnicastRangeRepository.findAll();
        assertThat(allocatedUnicastRangeList).hasSize(databaseSizeBeforeUpdate);
        AllocatedUnicastRange testAllocatedUnicastRange = allocatedUnicastRangeList.get(allocatedUnicastRangeList.size() - 1);
        assertThat(testAllocatedUnicastRange.getLowAddress()).isEqualTo(UPDATED_LOW_ADDRESS);
        assertThat(testAllocatedUnicastRange.getHighAddress()).isEqualTo(UPDATED_HIGH_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingAllocatedUnicastRange() throws Exception {
        int databaseSizeBeforeUpdate = allocatedUnicastRangeRepository.findAll().size();

        // Create the AllocatedUnicastRange

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAllocatedUnicastRangeMockMvc.perform(put("/api/allocated-unicast-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allocatedUnicastRange)))
            .andExpect(status().isBadRequest());

        // Validate the AllocatedUnicastRange in the database
        List<AllocatedUnicastRange> allocatedUnicastRangeList = allocatedUnicastRangeRepository.findAll();
        assertThat(allocatedUnicastRangeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAllocatedUnicastRange() throws Exception {
        // Initialize the database
        allocatedUnicastRangeRepository.saveAndFlush(allocatedUnicastRange);

        int databaseSizeBeforeDelete = allocatedUnicastRangeRepository.findAll().size();

        // Delete the allocatedUnicastRange
        restAllocatedUnicastRangeMockMvc.perform(delete("/api/allocated-unicast-ranges/{id}", allocatedUnicastRange.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AllocatedUnicastRange> allocatedUnicastRangeList = allocatedUnicastRangeRepository.findAll();
        assertThat(allocatedUnicastRangeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AllocatedUnicastRange.class);
        AllocatedUnicastRange allocatedUnicastRange1 = new AllocatedUnicastRange();
        allocatedUnicastRange1.setId(1L);
        AllocatedUnicastRange allocatedUnicastRange2 = new AllocatedUnicastRange();
        allocatedUnicastRange2.setId(allocatedUnicastRange1.getId());
        assertThat(allocatedUnicastRange1).isEqualTo(allocatedUnicastRange2);
        allocatedUnicastRange2.setId(2L);
        assertThat(allocatedUnicastRange1).isNotEqualTo(allocatedUnicastRange2);
        allocatedUnicastRange1.setId(null);
        assertThat(allocatedUnicastRange1).isNotEqualTo(allocatedUnicastRange2);
    }
}
