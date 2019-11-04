package skyy.blue.web.rest;

import skyy.blue.ProvisionercloudApp;
import skyy.blue.domain.MeshGroup;
import skyy.blue.repository.MeshGroupRepository;
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
 * Integration tests for the {@link MeshGroupResource} REST controller.
 */
@SpringBootTest(classes = ProvisionercloudApp.class)
public class MeshGroupResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private MeshGroupRepository meshGroupRepository;

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

    private MockMvc restMeshGroupMockMvc;

    private MeshGroup meshGroup;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MeshGroupResource meshGroupResource = new MeshGroupResource(meshGroupRepository);
        this.restMeshGroupMockMvc = MockMvcBuilders.standaloneSetup(meshGroupResource)
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
    public static MeshGroup createEntity(EntityManager em) {
        MeshGroup meshGroup = new MeshGroup()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .parentAddress(DEFAULT_PARENT_ADDRESS);
        return meshGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MeshGroup createUpdatedEntity(EntityManager em) {
        MeshGroup meshGroup = new MeshGroup()
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .parentAddress(UPDATED_PARENT_ADDRESS);
        return meshGroup;
    }

    @BeforeEach
    public void initTest() {
        meshGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createMeshGroup() throws Exception {
        int databaseSizeBeforeCreate = meshGroupRepository.findAll().size();

        // Create the MeshGroup
        restMeshGroupMockMvc.perform(post("/api/mesh-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meshGroup)))
            .andExpect(status().isCreated());

        // Validate the MeshGroup in the database
        List<MeshGroup> meshGroupList = meshGroupRepository.findAll();
        assertThat(meshGroupList).hasSize(databaseSizeBeforeCreate + 1);
        MeshGroup testMeshGroup = meshGroupList.get(meshGroupList.size() - 1);
        assertThat(testMeshGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMeshGroup.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testMeshGroup.getParentAddress()).isEqualTo(DEFAULT_PARENT_ADDRESS);
    }

    @Test
    @Transactional
    public void createMeshGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = meshGroupRepository.findAll().size();

        // Create the MeshGroup with an existing ID
        meshGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeshGroupMockMvc.perform(post("/api/mesh-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meshGroup)))
            .andExpect(status().isBadRequest());

        // Validate the MeshGroup in the database
        List<MeshGroup> meshGroupList = meshGroupRepository.findAll();
        assertThat(meshGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMeshGroups() throws Exception {
        // Initialize the database
        meshGroupRepository.saveAndFlush(meshGroup);

        // Get all the meshGroupList
        restMeshGroupMockMvc.perform(get("/api/mesh-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meshGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].parentAddress").value(hasItem(DEFAULT_PARENT_ADDRESS)));
    }
    
    @Test
    @Transactional
    public void getMeshGroup() throws Exception {
        // Initialize the database
        meshGroupRepository.saveAndFlush(meshGroup);

        // Get the meshGroup
        restMeshGroupMockMvc.perform(get("/api/mesh-groups/{id}", meshGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(meshGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.parentAddress").value(DEFAULT_PARENT_ADDRESS));
    }

    @Test
    @Transactional
    public void getNonExistingMeshGroup() throws Exception {
        // Get the meshGroup
        restMeshGroupMockMvc.perform(get("/api/mesh-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeshGroup() throws Exception {
        // Initialize the database
        meshGroupRepository.saveAndFlush(meshGroup);

        int databaseSizeBeforeUpdate = meshGroupRepository.findAll().size();

        // Update the meshGroup
        MeshGroup updatedMeshGroup = meshGroupRepository.findById(meshGroup.getId()).get();
        // Disconnect from session so that the updates on updatedMeshGroup are not directly saved in db
        em.detach(updatedMeshGroup);
        updatedMeshGroup
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .parentAddress(UPDATED_PARENT_ADDRESS);

        restMeshGroupMockMvc.perform(put("/api/mesh-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMeshGroup)))
            .andExpect(status().isOk());

        // Validate the MeshGroup in the database
        List<MeshGroup> meshGroupList = meshGroupRepository.findAll();
        assertThat(meshGroupList).hasSize(databaseSizeBeforeUpdate);
        MeshGroup testMeshGroup = meshGroupList.get(meshGroupList.size() - 1);
        assertThat(testMeshGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMeshGroup.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testMeshGroup.getParentAddress()).isEqualTo(UPDATED_PARENT_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingMeshGroup() throws Exception {
        int databaseSizeBeforeUpdate = meshGroupRepository.findAll().size();

        // Create the MeshGroup

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMeshGroupMockMvc.perform(put("/api/mesh-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meshGroup)))
            .andExpect(status().isBadRequest());

        // Validate the MeshGroup in the database
        List<MeshGroup> meshGroupList = meshGroupRepository.findAll();
        assertThat(meshGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMeshGroup() throws Exception {
        // Initialize the database
        meshGroupRepository.saveAndFlush(meshGroup);

        int databaseSizeBeforeDelete = meshGroupRepository.findAll().size();

        // Delete the meshGroup
        restMeshGroupMockMvc.perform(delete("/api/mesh-groups/{id}", meshGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MeshGroup> meshGroupList = meshGroupRepository.findAll();
        assertThat(meshGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeshGroup.class);
        MeshGroup meshGroup1 = new MeshGroup();
        meshGroup1.setId(1L);
        MeshGroup meshGroup2 = new MeshGroup();
        meshGroup2.setId(meshGroup1.getId());
        assertThat(meshGroup1).isEqualTo(meshGroup2);
        meshGroup2.setId(2L);
        assertThat(meshGroup1).isNotEqualTo(meshGroup2);
        meshGroup1.setId(null);
        assertThat(meshGroup1).isNotEqualTo(meshGroup2);
    }
}
