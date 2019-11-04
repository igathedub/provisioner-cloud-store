package skyy.blue.web.rest;

import skyy.blue.domain.AllocatedRange;
import skyy.blue.repository.AllocatedRangeRepository;
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
 * REST controller for managing {@link skyy.blue.domain.AllocatedRange}.
 */
@RestController
@RequestMapping("/api")
public class AllocatedRangeResource {

    private final Logger log = LoggerFactory.getLogger(AllocatedRangeResource.class);

    private static final String ENTITY_NAME = "allocatedRange";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AllocatedRangeRepository allocatedRangeRepository;

    public AllocatedRangeResource(AllocatedRangeRepository allocatedRangeRepository) {
        this.allocatedRangeRepository = allocatedRangeRepository;
    }

    /**
     * {@code POST  /allocated-ranges} : Create a new allocatedRange.
     *
     * @param allocatedRange the allocatedRange to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new allocatedRange, or with status {@code 400 (Bad Request)} if the allocatedRange has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/allocated-ranges")
    public ResponseEntity<AllocatedRange> createAllocatedRange(@RequestBody AllocatedRange allocatedRange) throws URISyntaxException {
        log.debug("REST request to save AllocatedRange : {}", allocatedRange);
        if (allocatedRange.getId() != null) {
            throw new BadRequestAlertException("A new allocatedRange cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AllocatedRange result = allocatedRangeRepository.save(allocatedRange);
        return ResponseEntity.created(new URI("/api/allocated-ranges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /allocated-ranges} : Updates an existing allocatedRange.
     *
     * @param allocatedRange the allocatedRange to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated allocatedRange,
     * or with status {@code 400 (Bad Request)} if the allocatedRange is not valid,
     * or with status {@code 500 (Internal Server Error)} if the allocatedRange couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/allocated-ranges")
    public ResponseEntity<AllocatedRange> updateAllocatedRange(@RequestBody AllocatedRange allocatedRange) throws URISyntaxException {
        log.debug("REST request to update AllocatedRange : {}", allocatedRange);
        if (allocatedRange.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AllocatedRange result = allocatedRangeRepository.save(allocatedRange);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, allocatedRange.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /allocated-ranges} : get all the allocatedRanges.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of allocatedRanges in body.
     */
    @GetMapping("/allocated-ranges")
    public ResponseEntity<List<AllocatedRange>> getAllAllocatedRanges(Pageable pageable) {
        log.debug("REST request to get a page of AllocatedRanges");
        Page<AllocatedRange> page = allocatedRangeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /allocated-ranges/:id} : get the "id" allocatedRange.
     *
     * @param id the id of the allocatedRange to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the allocatedRange, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/allocated-ranges/{id}")
    public ResponseEntity<AllocatedRange> getAllocatedRange(@PathVariable Long id) {
        log.debug("REST request to get AllocatedRange : {}", id);
        Optional<AllocatedRange> allocatedRange = allocatedRangeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(allocatedRange);
    }

    /**
     * {@code DELETE  /allocated-ranges/:id} : delete the "id" allocatedRange.
     *
     * @param id the id of the allocatedRange to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/allocated-ranges/{id}")
    public ResponseEntity<Void> deleteAllocatedRange(@PathVariable Long id) {
        log.debug("REST request to delete AllocatedRange : {}", id);
        allocatedRangeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
