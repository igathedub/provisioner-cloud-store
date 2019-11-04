package skyy.blue.web.rest;

import skyy.blue.domain.Provisioner;
import skyy.blue.repository.ProvisionerRepository;
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
 * REST controller for managing {@link skyy.blue.domain.Provisioner}.
 */
@RestController
@RequestMapping("/api")
public class ProvisionerResource {

    private final Logger log = LoggerFactory.getLogger(ProvisionerResource.class);

    private static final String ENTITY_NAME = "provisioner";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProvisionerRepository provisionerRepository;

    public ProvisionerResource(ProvisionerRepository provisionerRepository) {
        this.provisionerRepository = provisionerRepository;
    }

    /**
     * {@code POST  /provisioners} : Create a new provisioner.
     *
     * @param provisioner the provisioner to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new provisioner, or with status {@code 400 (Bad Request)} if the provisioner has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/provisioners")
    public ResponseEntity<Provisioner> createProvisioner(@RequestBody Provisioner provisioner) throws URISyntaxException {
        log.debug("REST request to save Provisioner : {}", provisioner);
        if (provisioner.getId() != null) {
            throw new BadRequestAlertException("A new provisioner cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Provisioner result = provisionerRepository.save(provisioner);
        return ResponseEntity.created(new URI("/api/provisioners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /provisioners} : Updates an existing provisioner.
     *
     * @param provisioner the provisioner to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated provisioner,
     * or with status {@code 400 (Bad Request)} if the provisioner is not valid,
     * or with status {@code 500 (Internal Server Error)} if the provisioner couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/provisioners")
    public ResponseEntity<Provisioner> updateProvisioner(@RequestBody Provisioner provisioner) throws URISyntaxException {
        log.debug("REST request to update Provisioner : {}", provisioner);
        if (provisioner.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Provisioner result = provisionerRepository.save(provisioner);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, provisioner.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /provisioners} : get all the provisioners.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of provisioners in body.
     */
    @GetMapping("/provisioners")
    public ResponseEntity<List<Provisioner>> getAllProvisioners(Pageable pageable) {
        log.debug("REST request to get a page of Provisioners");
        Page<Provisioner> page = provisionerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /provisioners/:id} : get the "id" provisioner.
     *
     * @param id the id of the provisioner to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the provisioner, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/provisioners/{id}")
    public ResponseEntity<Provisioner> getProvisioner(@PathVariable Long id) {
        log.debug("REST request to get Provisioner : {}", id);
        Optional<Provisioner> provisioner = provisionerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(provisioner);
    }

    /**
     * {@code DELETE  /provisioners/:id} : delete the "id" provisioner.
     *
     * @param id the id of the provisioner to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/provisioners/{id}")
    public ResponseEntity<Void> deleteProvisioner(@PathVariable Long id) {
        log.debug("REST request to delete Provisioner : {}", id);
        provisionerRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
