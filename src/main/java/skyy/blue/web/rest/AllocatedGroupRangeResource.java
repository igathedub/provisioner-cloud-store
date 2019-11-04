package skyy.blue.web.rest;

import skyy.blue.domain.AllocatedGroupRange;
import skyy.blue.repository.AllocatedGroupRangeRepository;
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
 * REST controller for managing {@link skyy.blue.domain.AllocatedGroupRange}.
 */
@RestController
@RequestMapping("/api")
public class AllocatedGroupRangeResource {

    private final Logger log = LoggerFactory.getLogger(AllocatedGroupRangeResource.class);

    private static final String ENTITY_NAME = "allocatedGroupRange";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AllocatedGroupRangeRepository allocatedGroupRangeRepository;

    public AllocatedGroupRangeResource(AllocatedGroupRangeRepository allocatedGroupRangeRepository) {
        this.allocatedGroupRangeRepository = allocatedGroupRangeRepository;
    }

    /**
     * {@code POST  /allocated-group-ranges} : Create a new allocatedGroupRange.
     *
     * @param allocatedGroupRange the allocatedGroupRange to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new allocatedGroupRange, or with status {@code 400 (Bad Request)} if the allocatedGroupRange has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/allocated-group-ranges")
    public ResponseEntity<AllocatedGroupRange> createAllocatedGroupRange(@RequestBody AllocatedGroupRange allocatedGroupRange) throws URISyntaxException {
        log.debug("REST request to save AllocatedGroupRange : {}", allocatedGroupRange);
        if (allocatedGroupRange.getId() != null) {
            throw new BadRequestAlertException("A new allocatedGroupRange cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AllocatedGroupRange result = allocatedGroupRangeRepository.save(allocatedGroupRange);
        return ResponseEntity.created(new URI("/api/allocated-group-ranges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /allocated-group-ranges} : Updates an existing allocatedGroupRange.
     *
     * @param allocatedGroupRange the allocatedGroupRange to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated allocatedGroupRange,
     * or with status {@code 400 (Bad Request)} if the allocatedGroupRange is not valid,
     * or with status {@code 500 (Internal Server Error)} if the allocatedGroupRange couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/allocated-group-ranges")
    public ResponseEntity<AllocatedGroupRange> updateAllocatedGroupRange(@RequestBody AllocatedGroupRange allocatedGroupRange) throws URISyntaxException {
        log.debug("REST request to update AllocatedGroupRange : {}", allocatedGroupRange);
        if (allocatedGroupRange.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AllocatedGroupRange result = allocatedGroupRangeRepository.save(allocatedGroupRange);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, allocatedGroupRange.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /allocated-group-ranges} : get all the allocatedGroupRanges.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of allocatedGroupRanges in body.
     */
    @GetMapping("/allocated-group-ranges")
    public ResponseEntity<List<AllocatedGroupRange>> getAllAllocatedGroupRanges(Pageable pageable) {
        log.debug("REST request to get a page of AllocatedGroupRanges");
        Page<AllocatedGroupRange> page = allocatedGroupRangeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /allocated-group-ranges/:id} : get the "id" allocatedGroupRange.
     *
     * @param id the id of the allocatedGroupRange to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the allocatedGroupRange, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/allocated-group-ranges/{id}")
    public ResponseEntity<AllocatedGroupRange> getAllocatedGroupRange(@PathVariable Long id) {
        log.debug("REST request to get AllocatedGroupRange : {}", id);
        Optional<AllocatedGroupRange> allocatedGroupRange = allocatedGroupRangeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(allocatedGroupRange);
    }

    /**
     * {@code DELETE  /allocated-group-ranges/:id} : delete the "id" allocatedGroupRange.
     *
     * @param id the id of the allocatedGroupRange to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/allocated-group-ranges/{id}")
    public ResponseEntity<Void> deleteAllocatedGroupRange(@PathVariable Long id) {
        log.debug("REST request to delete AllocatedGroupRange : {}", id);
        allocatedGroupRangeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
