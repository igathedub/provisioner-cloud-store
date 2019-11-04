package skyy.blue.web.rest;

import skyy.blue.domain.AllocatedSceneRange;
import skyy.blue.repository.AllocatedSceneRangeRepository;
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
 * REST controller for managing {@link skyy.blue.domain.AllocatedSceneRange}.
 */
@RestController
@RequestMapping("/api")
public class AllocatedSceneRangeResource {

    private final Logger log = LoggerFactory.getLogger(AllocatedSceneRangeResource.class);

    private static final String ENTITY_NAME = "allocatedSceneRange";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AllocatedSceneRangeRepository allocatedSceneRangeRepository;

    public AllocatedSceneRangeResource(AllocatedSceneRangeRepository allocatedSceneRangeRepository) {
        this.allocatedSceneRangeRepository = allocatedSceneRangeRepository;
    }

    /**
     * {@code POST  /allocated-scene-ranges} : Create a new allocatedSceneRange.
     *
     * @param allocatedSceneRange the allocatedSceneRange to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new allocatedSceneRange, or with status {@code 400 (Bad Request)} if the allocatedSceneRange has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/allocated-scene-ranges")
    public ResponseEntity<AllocatedSceneRange> createAllocatedSceneRange(@RequestBody AllocatedSceneRange allocatedSceneRange) throws URISyntaxException {
        log.debug("REST request to save AllocatedSceneRange : {}", allocatedSceneRange);
        if (allocatedSceneRange.getId() != null) {
            throw new BadRequestAlertException("A new allocatedSceneRange cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AllocatedSceneRange result = allocatedSceneRangeRepository.save(allocatedSceneRange);
        return ResponseEntity.created(new URI("/api/allocated-scene-ranges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /allocated-scene-ranges} : Updates an existing allocatedSceneRange.
     *
     * @param allocatedSceneRange the allocatedSceneRange to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated allocatedSceneRange,
     * or with status {@code 400 (Bad Request)} if the allocatedSceneRange is not valid,
     * or with status {@code 500 (Internal Server Error)} if the allocatedSceneRange couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/allocated-scene-ranges")
    public ResponseEntity<AllocatedSceneRange> updateAllocatedSceneRange(@RequestBody AllocatedSceneRange allocatedSceneRange) throws URISyntaxException {
        log.debug("REST request to update AllocatedSceneRange : {}", allocatedSceneRange);
        if (allocatedSceneRange.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AllocatedSceneRange result = allocatedSceneRangeRepository.save(allocatedSceneRange);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, allocatedSceneRange.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /allocated-scene-ranges} : get all the allocatedSceneRanges.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of allocatedSceneRanges in body.
     */
    @GetMapping("/allocated-scene-ranges")
    public ResponseEntity<List<AllocatedSceneRange>> getAllAllocatedSceneRanges(Pageable pageable) {
        log.debug("REST request to get a page of AllocatedSceneRanges");
        Page<AllocatedSceneRange> page = allocatedSceneRangeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /allocated-scene-ranges/:id} : get the "id" allocatedSceneRange.
     *
     * @param id the id of the allocatedSceneRange to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the allocatedSceneRange, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/allocated-scene-ranges/{id}")
    public ResponseEntity<AllocatedSceneRange> getAllocatedSceneRange(@PathVariable Long id) {
        log.debug("REST request to get AllocatedSceneRange : {}", id);
        Optional<AllocatedSceneRange> allocatedSceneRange = allocatedSceneRangeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(allocatedSceneRange);
    }

    /**
     * {@code DELETE  /allocated-scene-ranges/:id} : delete the "id" allocatedSceneRange.
     *
     * @param id the id of the allocatedSceneRange to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/allocated-scene-ranges/{id}")
    public ResponseEntity<Void> deleteAllocatedSceneRange(@PathVariable Long id) {
        log.debug("REST request to delete AllocatedSceneRange : {}", id);
        allocatedSceneRangeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
