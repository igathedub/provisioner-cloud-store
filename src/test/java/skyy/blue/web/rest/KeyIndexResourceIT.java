package skyy.blue.web.rest;

import skyy.blue.ProvisionercloudApp;
import skyy.blue.domain.KeyIndex;
import skyy.blue.repository.KeyIndexRepository;
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
 * Integration tests for the {@link KeyIndexResource} REST controller.
 */
@SpringBootTest(classes = ProvisionercloudApp.class)
public class KeyIndexResourceIT {

    private static final Integer DEFAULT_INDEX = 1;
    private static final Integer UPDATED_INDEX = 2;

    private static final Boolean DEFAULT_UPDATED = false;
    private static final Boolean UPDATED_UPDATED = true;

    @Autowired
    private KeyIndexRepository keyIndexRepository;

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

    private MockMvc restKeyIndexMockMvc;

    private KeyIndex keyIndex;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KeyIndexResource keyIndexResource = new KeyIndexResource(keyIndexRepository);
        this.restKeyIndexMockMvc = MockMvcBuilders.standaloneSetup(keyIndexResource)
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
    public static KeyIndex createEntity(EntityManager em) {
        KeyIndex keyIndex = new KeyIndex()
            .index(DEFAULT_INDEX)
            .updated(DEFAULT_UPDATED);
        return keyIndex;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KeyIndex createUpdatedEntity(EntityManager em) {
        KeyIndex keyIndex = new KeyIndex()
            .index(UPDATED_INDEX)
            .updated(UPDATED_UPDATED);
        return keyIndex;
    }

    @BeforeEach
    public void initTest() {
        keyIndex = createEntity(em);
    }

    @Test
    @Transactional
    public void createKeyIndex() throws Exception {
        int databaseSizeBeforeCreate = keyIndexRepository.findAll().size();

        // Create the KeyIndex
        restKeyIndexMockMvc.perform(post("/api/key-indices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(keyIndex)))
            .andExpect(status().isCreated());

        // Validate the KeyIndex in the database
        List<KeyIndex> keyIndexList = keyIndexRepository.findAll();
        assertThat(keyIndexList).hasSize(databaseSizeBeforeCreate + 1);
        KeyIndex testKeyIndex = keyIndexList.get(keyIndexList.size() - 1);
        assertThat(testKeyIndex.getIndex()).isEqualTo(DEFAULT_INDEX);
        assertThat(testKeyIndex.isUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createKeyIndexWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = keyIndexRepository.findAll().size();

        // Create the KeyIndex with an existing ID
        keyIndex.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKeyIndexMockMvc.perform(post("/api/key-indices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(keyIndex)))
            .andExpect(status().isBadRequest());

        // Validate the KeyIndex in the database
        List<KeyIndex> keyIndexList = keyIndexRepository.findAll();
        assertThat(keyIndexList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllKeyIndices() throws Exception {
        // Initialize the database
        keyIndexRepository.saveAndFlush(keyIndex);

        // Get all the keyIndexList
        restKeyIndexMockMvc.perform(get("/api/key-indices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(keyIndex.getId().intValue())))
            .andExpect(jsonPath("$.[*].index").value(hasItem(DEFAULT_INDEX)))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getKeyIndex() throws Exception {
        // Initialize the database
        keyIndexRepository.saveAndFlush(keyIndex);

        // Get the keyIndex
        restKeyIndexMockMvc.perform(get("/api/key-indices/{id}", keyIndex.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(keyIndex.getId().intValue()))
            .andExpect(jsonPath("$.index").value(DEFAULT_INDEX))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingKeyIndex() throws Exception {
        // Get the keyIndex
        restKeyIndexMockMvc.perform(get("/api/key-indices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKeyIndex() throws Exception {
        // Initialize the database
        keyIndexRepository.saveAndFlush(keyIndex);

        int databaseSizeBeforeUpdate = keyIndexRepository.findAll().size();

        // Update the keyIndex
        KeyIndex updatedKeyIndex = keyIndexRepository.findById(keyIndex.getId()).get();
        // Disconnect from session so that the updates on updatedKeyIndex are not directly saved in db
        em.detach(updatedKeyIndex);
        updatedKeyIndex
            .index(UPDATED_INDEX)
            .updated(UPDATED_UPDATED);

        restKeyIndexMockMvc.perform(put("/api/key-indices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedKeyIndex)))
            .andExpect(status().isOk());

        // Validate the KeyIndex in the database
        List<KeyIndex> keyIndexList = keyIndexRepository.findAll();
        assertThat(keyIndexList).hasSize(databaseSizeBeforeUpdate);
        KeyIndex testKeyIndex = keyIndexList.get(keyIndexList.size() - 1);
        assertThat(testKeyIndex.getIndex()).isEqualTo(UPDATED_INDEX);
        assertThat(testKeyIndex.isUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingKeyIndex() throws Exception {
        int databaseSizeBeforeUpdate = keyIndexRepository.findAll().size();

        // Create the KeyIndex

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKeyIndexMockMvc.perform(put("/api/key-indices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(keyIndex)))
            .andExpect(status().isBadRequest());

        // Validate the KeyIndex in the database
        List<KeyIndex> keyIndexList = keyIndexRepository.findAll();
        assertThat(keyIndexList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKeyIndex() throws Exception {
        // Initialize the database
        keyIndexRepository.saveAndFlush(keyIndex);

        int databaseSizeBeforeDelete = keyIndexRepository.findAll().size();

        // Delete the keyIndex
        restKeyIndexMockMvc.perform(delete("/api/key-indices/{id}", keyIndex.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KeyIndex> keyIndexList = keyIndexRepository.findAll();
        assertThat(keyIndexList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KeyIndex.class);
        KeyIndex keyIndex1 = new KeyIndex();
        keyIndex1.setId(1L);
        KeyIndex keyIndex2 = new KeyIndex();
        keyIndex2.setId(keyIndex1.getId());
        assertThat(keyIndex1).isEqualTo(keyIndex2);
        keyIndex2.setId(2L);
        assertThat(keyIndex1).isNotEqualTo(keyIndex2);
        keyIndex1.setId(null);
        assertThat(keyIndex1).isNotEqualTo(keyIndex2);
    }
}
