package skyy.blue.web.rest;

import skyy.blue.domain.MeshGroup;
import skyy.blue.repository.MeshGroupRepository;
import skyy.blue.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link skyy.blue.domain.MeshGroup}.
 */
@RestController
@RequestMapping("/api")
public class MeshGroupResource {

    private final Logger log = LoggerFactory.getLogger(MeshGroupResource.class);

    private static final String ENTITY_NAME = "meshGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MeshGroupRepository meshGroupRepository;

    public MeshGroupResource(MeshGroupRepository meshGroupRepository) {
        this.meshGroupRepository = meshGroupRepository;
    }

    /**
     * {@code POST  /mesh-groups} : Create a new meshGroup.
     *
     * @param meshGroup the meshGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new meshGroup, or with status {@code 400 (Bad Request)} if the meshGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mesh-groups")
    public ResponseEntity<MeshGroup> createMeshGroup(@RequestBody MeshGroup meshGroup) throws URISyntaxException {
        log.debug("REST request to save MeshGroup : {}", meshGroup);
        if (meshGroup.getId() != null) {
            throw new BadRequestAlertException("A new meshGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MeshGroup result = meshGroupRepository.save(meshGroup);
        return ResponseEntity.created(new URI("/api/mesh-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mesh-groups} : Updates an existing meshGroup.
     *
     * @param meshGroup the meshGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated meshGroup,
     * or with status {@code 400 (Bad Request)} if the meshGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the meshGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mesh-groups")
    public ResponseEntity<MeshGroup> updateMeshGroup(@RequestBody MeshGroup meshGroup) throws URISyntaxException {
        log.debug("REST request to update MeshGroup : {}", meshGroup);
        if (meshGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MeshGroup result = meshGroupRepository.save(meshGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, meshGroup.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mesh-groups} : get all the meshGroups.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of meshGroups in body.
     */
    @GetMapping("/mesh-groups")
    public ResponseEntity<List<MeshGroup>> getAllMeshGroups(Pageable pageable) {
        log.debug("REST request to get a page of MeshGroups");
        Page<MeshGroup> page = meshGroupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mesh-groups/:id} : get the "id" meshGroup.
     *
     * @param id the id of the meshGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the meshGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mesh-groups/{id}")
    public ResponseEntity<MeshGroup> getMeshGroup(@PathVariable Long id) {
        log.debug("REST request to get MeshGroup : {}", id);
        Optional<MeshGroup> meshGroup = meshGroupRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(meshGroup);
    }

    /**
     * {@code DELETE  /mesh-groups/:id} : delete the "id" meshGroup.
     *
     * @param id the id of the meshGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mesh-groups/{id}")
    public ResponseEntity<Void> deleteMeshGroup(@PathVariable Long id) {
        log.debug("REST request to delete MeshGroup : {}", id);
        meshGroupRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
