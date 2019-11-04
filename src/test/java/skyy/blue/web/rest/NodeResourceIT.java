package skyy.blue.web.rest;

import skyy.blue.ProvisionercloudApp;
import skyy.blue.domain.Node;
import skyy.blue.repository.NodeRepository;
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
 * Integration tests for the {@link NodeResource} REST controller.
 */
@SpringBootTest(classes = ProvisionercloudApp.class)
public class NodeResourceIT {

    private static final String DEFAULT_UNICAST_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_UNICAST_ADDRESS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CONFIG_COMPLETE = false;
    private static final Boolean UPDATED_CONFIG_COMPLETE = true;

    private static final Integer DEFAULT_DEFAULT_TTL = 1;
    private static final Integer UPDATED_DEFAULT_TTL = 2;

    private static final String DEFAULT_CID = "AAAAAAAAAA";
    private static final String UPDATED_CID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_BLACKLISTED = false;
    private static final Boolean UPDATED_BLACKLISTED = true;

    private static final String DEFAULT_U_UID = "AAAAAAAAAA";
    private static final String UPDATED_U_UID = "BBBBBBBBBB";

    private static final String DEFAULT_SECURITY = "AAAAAAAAAA";
    private static final String UPDATED_SECURITY = "BBBBBBBBBB";

    private static final String DEFAULT_CRPL = "AAAAAAAAAA";
    private static final String UPDATED_CRPL = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DEVICE_KEY = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VID = "AAAAAAAAAA";
    private static final String UPDATED_VID = "BBBBBBBBBB";

    private static final String DEFAULT_PID = "AAAAAAAAAA";
    private static final String UPDATED_PID = "BBBBBBBBBB";

    @Autowired
    private NodeRepository nodeRepository;

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

    private MockMvc restNodeMockMvc;

    private Node node;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NodeResource nodeResource = new NodeResource(nodeRepository);
        this.restNodeMockMvc = MockMvcBuilders.standaloneSetup(nodeResource)
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
    public static Node createEntity(EntityManager em) {
        Node node = new Node()
            .unicastAddress(DEFAULT_UNICAST_ADDRESS)
            .configComplete(DEFAULT_CONFIG_COMPLETE)
            .defaultTTL(DEFAULT_DEFAULT_TTL)
            .cid(DEFAULT_CID)
            .blacklisted(DEFAULT_BLACKLISTED)
            .uUID(DEFAULT_U_UID)
            .security(DEFAULT_SECURITY)
            .crpl(DEFAULT_CRPL)
            .name(DEFAULT_NAME)
            .deviceKey(DEFAULT_DEVICE_KEY)
            .vid(DEFAULT_VID)
            .pid(DEFAULT_PID);
        return node;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Node createUpdatedEntity(EntityManager em) {
        Node node = new Node()
            .unicastAddress(UPDATED_UNICAST_ADDRESS)
            .configComplete(UPDATED_CONFIG_COMPLETE)
            .defaultTTL(UPDATED_DEFAULT_TTL)
            .cid(UPDATED_CID)
            .blacklisted(UPDATED_BLACKLISTED)
            .uUID(UPDATED_U_UID)
            .security(UPDATED_SECURITY)
            .crpl(UPDATED_CRPL)
            .name(UPDATED_NAME)
            .deviceKey(UPDATED_DEVICE_KEY)
            .vid(UPDATED_VID)
            .pid(UPDATED_PID);
        return node;
    }

    @BeforeEach
    public void initTest() {
        node = createEntity(em);
    }

    @Test
    @Transactional
    public void createNode() throws Exception {
        int databaseSizeBeforeCreate = nodeRepository.findAll().size();

        // Create the Node
        restNodeMockMvc.perform(post("/api/nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(node)))
            .andExpect(status().isCreated());

        // Validate the Node in the database
        List<Node> nodeList = nodeRepository.findAll();
        assertThat(nodeList).hasSize(databaseSizeBeforeCreate + 1);
        Node testNode = nodeList.get(nodeList.size() - 1);
        assertThat(testNode.getUnicastAddress()).isEqualTo(DEFAULT_UNICAST_ADDRESS);
        assertThat(testNode.isConfigComplete()).isEqualTo(DEFAULT_CONFIG_COMPLETE);
        assertThat(testNode.getDefaultTTL()).isEqualTo(DEFAULT_DEFAULT_TTL);
        assertThat(testNode.getCid()).isEqualTo(DEFAULT_CID);
        assertThat(testNode.isBlacklisted()).isEqualTo(DEFAULT_BLACKLISTED);
        assertThat(testNode.getuUID()).isEqualTo(DEFAULT_U_UID);
        assertThat(testNode.getSecurity()).isEqualTo(DEFAULT_SECURITY);
        assertThat(testNode.getCrpl()).isEqualTo(DEFAULT_CRPL);
        assertThat(testNode.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNode.getDeviceKey()).isEqualTo(DEFAULT_DEVICE_KEY);
        assertThat(testNode.getVid()).isEqualTo(DEFAULT_VID);
        assertThat(testNode.getPid()).isEqualTo(DEFAULT_PID);
    }

    @Test
    @Transactional
    public void createNodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nodeRepository.findAll().size();

        // Create the Node with an existing ID
        node.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNodeMockMvc.perform(post("/api/nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(node)))
            .andExpect(status().isBadRequest());

        // Validate the Node in the database
        List<Node> nodeList = nodeRepository.findAll();
        assertThat(nodeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNodes() throws Exception {
        // Initialize the database
        nodeRepository.saveAndFlush(node);

        // Get all the nodeList
        restNodeMockMvc.perform(get("/api/nodes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(node.getId().intValue())))
            .andExpect(jsonPath("$.[*].unicastAddress").value(hasItem(DEFAULT_UNICAST_ADDRESS)))
            .andExpect(jsonPath("$.[*].configComplete").value(hasItem(DEFAULT_CONFIG_COMPLETE.booleanValue())))
            .andExpect(jsonPath("$.[*].defaultTTL").value(hasItem(DEFAULT_DEFAULT_TTL)))
            .andExpect(jsonPath("$.[*].cid").value(hasItem(DEFAULT_CID)))
            .andExpect(jsonPath("$.[*].blacklisted").value(hasItem(DEFAULT_BLACKLISTED.booleanValue())))
            .andExpect(jsonPath("$.[*].uUID").value(hasItem(DEFAULT_U_UID)))
            .andExpect(jsonPath("$.[*].security").value(hasItem(DEFAULT_SECURITY)))
            .andExpect(jsonPath("$.[*].crpl").value(hasItem(DEFAULT_CRPL)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].deviceKey").value(hasItem(DEFAULT_DEVICE_KEY)))
            .andExpect(jsonPath("$.[*].vid").value(hasItem(DEFAULT_VID)))
            .andExpect(jsonPath("$.[*].pid").value(hasItem(DEFAULT_PID)));
    }
    
    @Test
    @Transactional
    public void getNode() throws Exception {
        // Initialize the database
        nodeRepository.saveAndFlush(node);

        // Get the node
        restNodeMockMvc.perform(get("/api/nodes/{id}", node.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(node.getId().intValue()))
            .andExpect(jsonPath("$.unicastAddress").value(DEFAULT_UNICAST_ADDRESS))
            .andExpect(jsonPath("$.configComplete").value(DEFAULT_CONFIG_COMPLETE.booleanValue()))
            .andExpect(jsonPath("$.defaultTTL").value(DEFAULT_DEFAULT_TTL))
            .andExpect(jsonPath("$.cid").value(DEFAULT_CID))
            .andExpect(jsonPath("$.blacklisted").value(DEFAULT_BLACKLISTED.booleanValue()))
            .andExpect(jsonPath("$.uUID").value(DEFAULT_U_UID))
            .andExpect(jsonPath("$.security").value(DEFAULT_SECURITY))
            .andExpect(jsonPath("$.crpl").value(DEFAULT_CRPL))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.deviceKey").value(DEFAULT_DEVICE_KEY))
            .andExpect(jsonPath("$.vid").value(DEFAULT_VID))
            .andExpect(jsonPath("$.pid").value(DEFAULT_PID));
    }

    @Test
    @Transactional
    public void getNonExistingNode() throws Exception {
        // Get the node
        restNodeMockMvc.perform(get("/api/nodes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNode() throws Exception {
        // Initialize the database
        nodeRepository.saveAndFlush(node);

        int databaseSizeBeforeUpdate = nodeRepository.findAll().size();

        // Update the node
        Node updatedNode = nodeRepository.findById(node.getId()).get();
        // Disconnect from session so that the updates on updatedNode are not directly saved in db
        em.detach(updatedNode);
        updatedNode
            .unicastAddress(UPDATED_UNICAST_ADDRESS)
            .configComplete(UPDATED_CONFIG_COMPLETE)
            .defaultTTL(UPDATED_DEFAULT_TTL)
            .cid(UPDATED_CID)
            .blacklisted(UPDATED_BLACKLISTED)
            .uUID(UPDATED_U_UID)
            .security(UPDATED_SECURITY)
            .crpl(UPDATED_CRPL)
            .name(UPDATED_NAME)
            .deviceKey(UPDATED_DEVICE_KEY)
            .vid(UPDATED_VID)
            .pid(UPDATED_PID);

        restNodeMockMvc.perform(put("/api/nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNode)))
            .andExpect(status().isOk());

        // Validate the Node in the database
        List<Node> nodeList = nodeRepository.findAll();
        assertThat(nodeList).hasSize(databaseSizeBeforeUpdate);
        Node testNode = nodeList.get(nodeList.size() - 1);
        assertThat(testNode.getUnicastAddress()).isEqualTo(UPDATED_UNICAST_ADDRESS);
        assertThat(testNode.isConfigComplete()).isEqualTo(UPDATED_CONFIG_COMPLETE);
        assertThat(testNode.getDefaultTTL()).isEqualTo(UPDATED_DEFAULT_TTL);
        assertThat(testNode.getCid()).isEqualTo(UPDATED_CID);
        assertThat(testNode.isBlacklisted()).isEqualTo(UPDATED_BLACKLISTED);
        assertThat(testNode.getuUID()).isEqualTo(UPDATED_U_UID);
        assertThat(testNode.getSecurity()).isEqualTo(UPDATED_SECURITY);
        assertThat(testNode.getCrpl()).isEqualTo(UPDATED_CRPL);
        assertThat(testNode.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNode.getDeviceKey()).isEqualTo(UPDATED_DEVICE_KEY);
        assertThat(testNode.getVid()).isEqualTo(UPDATED_VID);
        assertThat(testNode.getPid()).isEqualTo(UPDATED_PID);
    }

    @Test
    @Transactional
    public void updateNonExistingNode() throws Exception {
        int databaseSizeBeforeUpdate = nodeRepository.findAll().size();

        // Create the Node

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNodeMockMvc.perform(put("/api/nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(node)))
            .andExpect(status().isBadRequest());

        // Validate the Node in the database
        List<Node> nodeList = nodeRepository.findAll();
        assertThat(nodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNode() throws Exception {
        // Initialize the database
        nodeRepository.saveAndFlush(node);

        int databaseSizeBeforeDelete = nodeRepository.findAll().size();

        // Delete the node
        restNodeMockMvc.perform(delete("/api/nodes/{id}", node.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Node> nodeList = nodeRepository.findAll();
        assertThat(nodeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Node.class);
        Node node1 = new Node();
        node1.setId(1L);
        Node node2 = new Node();
        node2.setId(node1.getId());
        assertThat(node1).isEqualTo(node2);
        node2.setId(2L);
        assertThat(node1).isNotEqualTo(node2);
        node1.setId(null);
        assertThat(node1).isNotEqualTo(node2);
    }
}
