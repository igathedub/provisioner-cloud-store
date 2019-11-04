package skyy.blue.web.rest;

import skyy.blue.domain.Facility;
import skyy.blue.repository.FacilityRepository;
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
 * REST controller for managing {@link skyy.blue.domain.Facility}.
 */
@RestController
@RequestMapping("/api")
public class FacilityResource {

    private final Logger log = LoggerFactory.getLogger(FacilityResource.class);

    private static final String ENTITY_NAME = "facility";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FacilityRepository facilityRepository;

    public FacilityResource(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    /**
     * {@code POST  /facilities} : Create a new facility.
     *
     * @param facility the facility to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new facility, or with status {@code 400 (Bad Request)} if the facility has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/facilities")
    public ResponseEntity<Facility> createFacility(@RequestBody Facility facility) throws URISyntaxException {
        log.debug("REST request to save Facility : {}", facility);
        if (facility.getId() != null) {
            throw new BadRequestAlertException("A new facility cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Facility result = facilityRepository.save(facility);
        return ResponseEntity.created(new URI("/api/facilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /facilities} : Updates an existing facility.
     *
     * @param facility the facility to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated facility,
     * or with status {@code 400 (Bad Request)} if the facility is not valid,
     * or with status {@code 500 (Internal Server Error)} if the facility couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/facilities")
    public ResponseEntity<Facility> updateFacility(@RequestBody Facility facility) throws URISyntaxException {
        log.debug("REST request to update Facility : {}", facility);
        if (facility.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Facility result = facilityRepository.save(facility);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, facility.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /facilities} : get all the facilities.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of facilities in body.
     */
    @GetMapping("/facilities")
    public ResponseEntity<List<Facility>> getAllFacilities(Pageable pageable) {
        log.debug("REST request to get a page of Facilities");
        Page<Facility> page = facilityRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /facilities/:id} : get the "id" facility.
     *
     * @param id the id of the facility to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the facility, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/facilities/{id}")
    public ResponseEntity<Facility> getFacility(@PathVariable Long id) {
        log.debug("REST request to get Facility : {}", id);
        Optional<Facility> facility = facilityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(facility);
    }

    /**
     * {@code DELETE  /facilities/:id} : delete the "id" facility.
     *
     * @param id the id of the facility to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/facilities/{id}")
    public ResponseEntity<Void> deleteFacility(@PathVariable Long id) {
        log.debug("REST request to delete Facility : {}", id);
        facilityRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
