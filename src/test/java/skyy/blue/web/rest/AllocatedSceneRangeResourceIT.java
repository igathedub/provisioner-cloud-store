package skyy.blue.web.rest;

import skyy.blue.ProvisionercloudApp;
import skyy.blue.domain.AllocatedSceneRange;
import skyy.blue.repository.AllocatedSceneRangeRepository;
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
 * Integration tests for the {@link AllocatedSceneRangeResource} REST controller.
 */
@SpringBootTest(classes = ProvisionercloudApp.class)
public class AllocatedSceneRangeResourceIT {

    private static final String DEFAULT_LOW_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_LOW_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_HIGH_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_HIGH_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private AllocatedSceneRangeRepository allocatedSceneRangeRepository;

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

    private MockMvc restAllocatedSceneRangeMockMvc;

    private AllocatedSceneRange allocatedSceneRange;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AllocatedSceneRangeResource allocatedSceneRangeResource = new AllocatedSceneRangeResource(allocatedSceneRangeRepository);
        this.restAllocatedSceneRangeMockMvc = MockMvcBuilders.standaloneSetup(allocatedSceneRangeResource)
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
    public static AllocatedSceneRange createEntity(EntityManager em) {
        AllocatedSceneRange allocatedSceneRange = new AllocatedSceneRange()
            .lowAddress(DEFAULT_LOW_ADDRESS)
            .highAddress(DEFAULT_HIGH_ADDRESS);
        return allocatedSceneRange;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AllocatedSceneRange createUpdatedEntity(EntityManager em) {
        AllocatedSceneRange allocatedSceneRange = new AllocatedSceneRange()
            .lowAddress(UPDATED_LOW_ADDRESS)
            .highAddress(UPDATED_HIGH_ADDRESS);
        return allocatedSceneRange;
    }

    @BeforeEach
    public void initTest() {
        allocatedSceneRange = createEntity(em);
    }

    @Test
    @Transactional
    public void createAllocatedSceneRange() throws Exception {
        int databaseSizeBeforeCreate = allocatedSceneRangeRepository.findAll().size();

        // Create the AllocatedSceneRange
        restAllocatedSceneRangeMockMvc.perform(post("/api/allocated-scene-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allocatedSceneRange)))
            .andExpect(status().isCreated());

        // Validate the AllocatedSceneRange in the database
        List<AllocatedSceneRange> allocatedSceneRangeList = allocatedSceneRangeRepository.findAll();
        assertThat(allocatedSceneRangeList).hasSize(databaseSizeBeforeCreate + 1);
        AllocatedSceneRange testAllocatedSceneRange = allocatedSceneRangeList.get(allocatedSceneRangeList.size() - 1);
        assertThat(testAllocatedSceneRange.getLowAddress()).isEqualTo(DEFAULT_LOW_ADDRESS);
        assertThat(testAllocatedSceneRange.getHighAddress()).isEqualTo(DEFAULT_HIGH_ADDRESS);
    }

    @Test
    @Transactional
    public void createAllocatedSceneRangeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = allocatedSceneRangeRepository.findAll().size();

        // Create the AllocatedSceneRange with an existing ID
        allocatedSceneRange.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAllocatedSceneRangeMockMvc.perform(post("/api/allocated-scene-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allocatedSceneRange)))
            .andExpect(status().isBadRequest());

        // Validate the AllocatedSceneRange in the database
        List<AllocatedSceneRange> allocatedSceneRangeList = allocatedSceneRangeRepository.findAll();
        assertThat(allocatedSceneRangeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAllocatedSceneRanges() throws Exception {
        // Initialize the database
        allocatedSceneRangeRepository.saveAndFlush(allocatedSceneRange);

        // Get all the allocatedSceneRangeList
        restAllocatedSceneRangeMockMvc.perform(get("/api/allocated-scene-ranges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(allocatedSceneRange.getId().intValue())))
            .andExpect(jsonPath("$.[*].lowAddress").value(hasItem(DEFAULT_LOW_ADDRESS)))
            .andExpect(jsonPath("$.[*].highAddress").value(hasItem(DEFAULT_HIGH_ADDRESS)));
    }
    
    @Test
    @Transactional
    public void getAllocatedSceneRange() throws Exception {
        // Initialize the database
        allocatedSceneRangeRepository.saveAndFlush(allocatedSceneRange);

        // Get the allocatedSceneRange
        restAllocatedSceneRangeMockMvc.perform(get("/api/allocated-scene-ranges/{id}", allocatedSceneRange.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(allocatedSceneRange.getId().intValue()))
            .andExpect(jsonPath("$.lowAddress").value(DEFAULT_LOW_ADDRESS))
            .andExpect(jsonPath("$.highAddress").value(DEFAULT_HIGH_ADDRESS));
    }

    @Test
    @Transactional
    public void getNonExistingAllocatedSceneRange() throws Exception {
        // Get the allocatedSceneRange
        restAllocatedSceneRangeMockMvc.perform(get("/api/allocated-scene-ranges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAllocatedSceneRange() throws Exception {
        // Initialize the database
        allocatedSceneRangeRepository.saveAndFlush(allocatedSceneRange);

        int databaseSizeBeforeUpdate = allocatedSceneRangeRepository.findAll().size();

        // Update the allocatedSceneRange
        AllocatedSceneRange updatedAllocatedSceneRange = allocatedSceneRangeRepository.findById(allocatedSceneRange.getId()).get();
        // Disconnect from session so that the updates on updatedAllocatedSceneRange are not directly saved in db
        em.detach(updatedAllocatedSceneRange);
        updatedAllocatedSceneRange
            .lowAddress(UPDATED_LOW_ADDRESS)
            .highAddress(UPDATED_HIGH_ADDRESS);

        restAllocatedSceneRangeMockMvc.perform(put("/api/allocated-scene-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAllocatedSceneRange)))
            .andExpect(status().isOk());

        // Validate the AllocatedSceneRange in the database
        List<AllocatedSceneRange> allocatedSceneRangeList = allocatedSceneRangeRepository.findAll();
        assertThat(allocatedSceneRangeList).hasSize(databaseSizeBeforeUpdate);
        AllocatedSceneRange testAllocatedSceneRange = allocatedSceneRangeList.get(allocatedSceneRangeList.size() - 1);
        assertThat(testAllocatedSceneRange.getLowAddress()).isEqualTo(UPDATED_LOW_ADDRESS);
        assertThat(testAllocatedSceneRange.getHighAddress()).isEqualTo(UPDATED_HIGH_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingAllocatedSceneRange() throws Exception {
        int databaseSizeBeforeUpdate = allocatedSceneRangeRepository.findAll().size();

        // Create the AllocatedSceneRange

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAllocatedSceneRangeMockMvc.perform(put("/api/allocated-scene-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allocatedSceneRange)))
            .andExpect(status().isBadRequest());

        // Validate the AllocatedSceneRange in the database
        List<AllocatedSceneRange> allocatedSceneRangeList = allocatedSceneRangeRepository.findAll();
        assertThat(allocatedSceneRangeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAllocatedSceneRange() throws Exception {
        // Initialize the database
        allocatedSceneRangeRepository.saveAndFlush(allocatedSceneRange);

        int databaseSizeBeforeDelete = allocatedSceneRangeRepository.findAll().size();

        // Delete the allocatedSceneRange
        restAllocatedSceneRangeMockMvc.perform(delete("/api/allocated-scene-ranges/{id}", allocatedSceneRange.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AllocatedSceneRange> allocatedSceneRangeList = allocatedSceneRangeRepository.findAll();
        assertThat(allocatedSceneRangeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AllocatedSceneRange.class);
        AllocatedSceneRange allocatedSceneRange1 = new AllocatedSceneRange();
        allocatedSceneRange1.setId(1L);
        AllocatedSceneRange allocatedSceneRange2 = new AllocatedSceneRange();
        allocatedSceneRange2.setId(allocatedSceneRange1.getId());
        assertThat(allocatedSceneRange1).isEqualTo(allocatedSceneRange2);
        allocatedSceneRange2.setId(2L);
        assertThat(allocatedSceneRange1).isNotEqualTo(allocatedSceneRange2);
        allocatedSceneRange1.setId(null);
        assertThat(allocatedSceneRange1).isNotEqualTo(allocatedSceneRange2);
    }
}
