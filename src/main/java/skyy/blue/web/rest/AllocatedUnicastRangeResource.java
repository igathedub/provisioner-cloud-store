package skyy.blue.web.rest;

import skyy.blue.domain.AllocatedUnicastRange;
import skyy.blue.repository.AllocatedUnicastRangeRepository;
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
 * REST controller for managing {@link skyy.blue.domain.AllocatedUnicastRange}.
 */
@RestController
@RequestMapping("/api")
public class AllocatedUnicastRangeResource {

    private final Logger log = LoggerFactory.getLogger(AllocatedUnicastRangeResource.class);

    private static final String ENTITY_NAME = "allocatedUnicastRange";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AllocatedUnicastRangeRepository allocatedUnicastRangeRepository;

    public AllocatedUnicastRangeResource(AllocatedUnicastRangeRepository allocatedUnicastRangeRepository) {
        this.allocatedUnicastRangeRepository = allocatedUnicastRangeRepository;
    }

    /**
     * {@code POST  /allocated-unicast-ranges} : Create a new allocatedUnicastRange.
     *
     * @param allocatedUnicastRange the allocatedUnicastRange to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new allocatedUnicastRange, or with status {@code 400 (Bad Request)} if the allocatedUnicastRange has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/allocated-unicast-ranges")
    public ResponseEntity<AllocatedUnicastRange> createAllocatedUnicastRange(@RequestBody AllocatedUnicastRange allocatedUnicastRange) throws URISyntaxException {
        log.debug("REST request to save AllocatedUnicastRange : {}", allocatedUnicastRange);
        if (allocatedUnicastRange.getId() != null) {
            throw new BadRequestAlertException("A new allocatedUnicastRange cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AllocatedUnicastRange result = allocatedUnicastRangeRepository.save(allocatedUnicastRange);
        return ResponseEntity.created(new URI("/api/allocated-unicast-ranges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /allocated-unicast-ranges} : Updates an existing allocatedUnicastRange.
     *
     * @param allocatedUnicastRange the allocatedUnicastRange to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated allocatedUnicastRange,
     * or with status {@code 400 (Bad Request)} if the allocatedUnicastRange is not valid,
     * or with status {@code 500 (Internal Server Error)} if the allocatedUnicastRange couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/allocated-unicast-ranges")
    public ResponseEntity<AllocatedUnicastRange> updateAllocatedUnicastRange(@RequestBody AllocatedUnicastRange allocatedUnicastRange) throws URISyntaxException {
        log.debug("REST request to update AllocatedUnicastRange : {}", allocatedUnicastRange);
        if (allocatedUnicastRange.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AllocatedUnicastRange result = allocatedUnicastRangeRepository.save(allocatedUnicastRange);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, allocatedUnicastRange.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /allocated-unicast-ranges} : get all the allocatedUnicastRanges.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of allocatedUnicastRanges in body.
     */
    @GetMapping("/allocated-unicast-ranges")
    public ResponseEntity<List<AllocatedUnicastRange>> getAllAllocatedUnicastRanges(Pageable pageable) {
        log.debug("REST request to get a page of AllocatedUnicastRanges");
        Page<AllocatedUnicastRange> page = allocatedUnicastRangeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /allocated-unicast-ranges/:id} : get the "id" allocatedUnicastRange.
     *
     * @param id the id of the allocatedUnicastRange to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the allocatedUnicastRange, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/allocated-unicast-ranges/{id}")
    public ResponseEntity<AllocatedUnicastRange> getAllocatedUnicastRange(@PathVariable Long id) {
        log.debug("REST request to get AllocatedUnicastRange : {}", id);
        Optional<AllocatedUnicastRange> allocatedUnicastRange = allocatedUnicastRangeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(allocatedUnicastRange);
    }

    /**
     * {@code DELETE  /allocated-unicast-ranges/:id} : delete the "id" allocatedUnicastRange.
     *
     * @param id the id of the allocatedUnicastRange to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/allocated-unicast-ranges/{id}")
    public ResponseEntity<Void> deleteAllocatedUnicastRange(@PathVariable Long id) {
        log.debug("REST request to delete AllocatedUnicastRange : {}", id);
        allocatedUnicastRangeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
