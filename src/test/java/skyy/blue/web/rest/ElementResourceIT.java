package skyy.blue.web.rest;

import skyy.blue.ProvisionercloudApp;
import skyy.blue.domain.Element;
import skyy.blue.repository.ElementRepository;
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
 * Integration tests for the {@link ElementResource} REST controller.
 */
@SpringBootTest(classes = ProvisionercloudApp.class)
public class ElementResourceIT {

    private static final Integer DEFAULT_INDEX = 1;
    private static final Integer UPDATED_INDEX = 2;

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ElementRepository elementRepository;

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

    private MockMvc restElementMockMvc;

    private Element element;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ElementResource elementResource = new ElementResource(elementRepository);
        this.restElementMockMvc = MockMvcBuilders.standaloneSetup(elementResource)
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
    public static Element createEntity(EntityManager em) {
        Element element = new Element()
            .index(DEFAULT_INDEX)
            .location(DEFAULT_LOCATION)
            .name(DEFAULT_NAME);
        return element;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Element createUpdatedEntity(EntityManager em) {
        Element element = new Element()
            .index(UPDATED_INDEX)
            .location(UPDATED_LOCATION)
            .name(UPDATED_NAME);
        return element;
    }

    @BeforeEach
    public void initTest() {
        element = createEntity(em);
    }

    @Test
    @Transactional
    public void createElement() throws Exception {
        int databaseSizeBeforeCreate = elementRepository.findAll().size();

        // Create the Element
        restElementMockMvc.perform(post("/api/elements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(element)))
            .andExpect(status().isCreated());

        // Validate the Element in the database
        List<Element> elementList = elementRepository.findAll();
        assertThat(elementList).hasSize(databaseSizeBeforeCreate + 1);
        Element testElement = elementList.get(elementList.size() - 1);
        assertThat(testElement.getIndex()).isEqualTo(DEFAULT_INDEX);
        assertThat(testElement.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testElement.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createElementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = elementRepository.findAll().size();

        // Create the Element with an existing ID
        element.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restElementMockMvc.perform(post("/api/elements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(element)))
            .andExpect(status().isBadRequest());

        // Validate the Element in the database
        List<Element> elementList = elementRepository.findAll();
        assertThat(elementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllElements() throws Exception {
        // Initialize the database
        elementRepository.saveAndFlush(element);

        // Get all the elementList
        restElementMockMvc.perform(get("/api/elements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(element.getId().intValue())))
            .andExpect(jsonPath("$.[*].index").value(hasItem(DEFAULT_INDEX)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getElement() throws Exception {
        // Initialize the database
        elementRepository.saveAndFlush(element);

        // Get the element
        restElementMockMvc.perform(get("/api/elements/{id}", element.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(element.getId().intValue()))
            .andExpect(jsonPath("$.index").value(DEFAULT_INDEX))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingElement() throws Exception {
        // Get the element
        restElementMockMvc.perform(get("/api/elements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateElement() throws Exception {
        // Initialize the database
        elementRepository.saveAndFlush(element);

        int databaseSizeBeforeUpdate = elementRepository.findAll().size();

        // Update the element
        Element updatedElement = elementRepository.findById(element.getId()).get();
        // Disconnect from session so that the updates on updatedElement are not directly saved in db
        em.detach(updatedElement);
        updatedElement
            .index(UPDATED_INDEX)
            .location(UPDATED_LOCATION)
            .name(UPDATED_NAME);

        restElementMockMvc.perform(put("/api/elements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedElement)))
            .andExpect(status().isOk());

        // Validate the Element in the database
        List<Element> elementList = elementRepository.findAll();
        assertThat(elementList).hasSize(databaseSizeBeforeUpdate);
        Element testElement = elementList.get(elementList.size() - 1);
        assertThat(testElement.getIndex()).isEqualTo(UPDATED_INDEX);
        assertThat(testElement.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testElement.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingElement() throws Exception {
        int databaseSizeBeforeUpdate = elementRepository.findAll().size();

        // Create the Element

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElementMockMvc.perform(put("/api/elements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(element)))
            .andExpect(status().isBadRequest());

        // Validate the Element in the database
        List<Element> elementList = elementRepository.findAll();
        assertThat(elementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteElement() throws Exception {
        // Initialize the database
        elementRepository.saveAndFlush(element);

        int databaseSizeBeforeDelete = elementRepository.findAll().size();

        // Delete the element
        restElementMockMvc.perform(delete("/api/elements/{id}", element.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Element> elementList = elementRepository.findAll();
        assertThat(elementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Element.class);
        Element element1 = new Element();
        element1.setId(1L);
        Element element2 = new Element();
        element2.setId(element1.getId());
        assertThat(element1).isEqualTo(element2);
        element2.setId(2L);
        assertThat(element1).isNotEqualTo(element2);
        element1.setId(null);
        assertThat(element1).isNotEqualTo(element2);
    }
}
